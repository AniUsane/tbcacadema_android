package com.example.tbcacademy.presentation.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log.d
import android.util.Log.e
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tbcacademy.domain.usecase.UploadImageToFirebaseUseCase
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File

@HiltWorker
class UploadImageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
    ): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        //get file path and name from data
        val filePath = inputData.getString("image_path")
        val fileName = inputData.getString("file_name")

        if (filePath == null || fileName == null) {
            return Result.failure()
        }

        val file = File(filePath)
        if (!file.exists()) {
            return Result.failure()
        }

        //decode file to bitmap
        val bitmap = BitmapFactory.decodeFile(filePath)
        if (bitmap == null) {
            return Result.failure()
        }

        //converts bitmap to bytearray and uploads it to firebase storage
        return try {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            val byteArray = stream.toByteArray()

            val ref = Firebase.storage.reference.child("images/$fileName.jpg")
            ref.putBytes(byteArray).await()

            Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }


}