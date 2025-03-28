package com.example.tbcacademy.data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.tbcacademy.domain.usecase.UploadImageToFirebaseUseCase
import com.example.tbcacademy.domain.usecase.UploadImageUseCase
import com.example.tbcacademy.presentation.screen.UploadImageWorker
import com.google.android.datatransport.runtime.logging.Logging.d
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

class UploadImageRepository @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadImageToFirebaseUseCase: UploadImageToFirebaseUseCase,
    private val context: Context
) {
    //gets and compresses captured image
    fun getCameraImage(data: Intent?): Bitmap? {
        val bitmap = uploadImageUseCase.handleCameraResult(data)
        return bitmap?.let { compressImage(it) }
    }

    //gets image URI from gallery
    fun getGalleryImage(data: Intent): Uri? {
        return uploadImageUseCase.handleGalleryResult(data)
    }

    //gets and compresses image from gallery
    fun getCompressedBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            compressImage(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //compress image method
    private fun compressImage(bitmap: Bitmap): Bitmap {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val byteArray = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    //saves bitmap in a file and schedules upload with work manager
    fun uploadImageWithWorker(bitmap: Bitmap): UUID {
        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }

        val data = workDataOf(
            "image_path" to file.absolutePath,
            "file_name" to file.nameWithoutExtension
        )

        val request = OneTimeWorkRequestBuilder<UploadImageWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
        return request.id
    }
}