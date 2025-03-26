package com.example.tbcacademy.presentation.screen

import android.content.Intent

sealed class UploadImageEvent {
    data object OpenCamera: UploadImageEvent()
    data object OpenGallery: UploadImageEvent()
    data class ImageCaptured(val data: Intent?): UploadImageEvent()
    data class ImageSelected(val data: Intent): UploadImageEvent()
}