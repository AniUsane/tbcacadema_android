package com.example.tbcacademy.domain.usecase

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import javax.inject.Inject

class UploadImageUseCase @Inject constructor() {

    fun handleCameraResult(data: Intent?): Bitmap?=
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            data?.extras?.getParcelable("data", Bitmap::class.java)
        } else {
            null
        }

    fun handleGalleryResult(data: Intent?): Uri? {
        return data?.data
    }
}