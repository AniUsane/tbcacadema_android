package com.example.tbcacademy.presentation.screen

import android.graphics.Bitmap
import android.net.Uri

sealed class UploadImageState {
    data object Idle: UploadImageState()
    data object Loading: UploadImageState()
    data class ImageLoaded(val bitmap: Bitmap): UploadImageState()
    data class UriLoaded(val uri: Uri?): UploadImageState()
    data class Error(val message: String): UploadImageState()
}