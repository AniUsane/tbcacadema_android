package com.example.tbcacademy.presentation.screen

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.BaseViewModel
import com.example.tbcacademy.data.repository.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UploadImageViewModel @Inject constructor(
    private val repository: UploadImageRepository
): BaseViewModel<UploadImageState, UploadImageEvent, UploadImageEffect>(
    initialState = UploadImageState.Idle
) {
    private var lastUploadedId: UUID? = null

    //handles ui events and updates state
    override fun obtainEvent(event: UploadImageEvent) {
        when (event) {
            is UploadImageEvent.OpenCamera -> updateState { UploadImageState.Idle }
            is UploadImageEvent.OpenGallery -> updateState { UploadImageState.Idle }
            is UploadImageEvent.ImageCaptured -> handleCameraResult(event.data)
            is UploadImageEvent.ImageSelected -> handleGalleryResult(event.data)
            is UploadImageEvent.UploadImage -> uploadSelectedImage(event.bitmap)
        }

    }

    //processes image from camera(gets image and compresses from repository)
    private fun handleCameraResult(data: Intent?) {
        viewModelScope.launch {
            val bitmap = repository.getCameraImage(data)
            if (bitmap != null) {
                updateState { UploadImageState.ImageLoaded(bitmap) }
            } else {
                emitEffect(UploadImageEffect.ShowError("Failed to capture image"))
            }
        }
    }

    //processes image from gallery
    private fun handleGalleryResult(data: Intent) {
        viewModelScope.launch {
            val uri = repository.getGalleryImage(data)
            if (uri != null) {
                val compressedBitmap = repository.getCompressedBitmapFromUri(uri)
                if (compressedBitmap != null) {
                    updateState { UploadImageState.ImageLoaded(compressedBitmap) }
                } else {
                    updateState { UploadImageState.Error("Failed to load image") }
                }
            } else {
                emitEffect(UploadImageEffect.ShowError("Failed to select image"))
            }
        }
    }

    private fun uploadSelectedImage(bitmap: Bitmap){
        updateState { UploadImageState.Loading }
        lastUploadedId = repository.uploadImageWithWorker(bitmap)
    }
    fun getUploadWorkerId(): UUID? = lastUploadedId
}