package com.example.tbcacademy.data.repository

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.tbcacademy.domain.usecase.UploadImageUseCase
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

class UploadImageRepository @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase,
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
}