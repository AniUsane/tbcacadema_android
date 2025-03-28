package com.example.tbcacademy.domain.usecase

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class UploadImageToFirebaseUseCase @Inject constructor() {

    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }
}