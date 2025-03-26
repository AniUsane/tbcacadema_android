package com.example.tbcacademy.presentation.screen

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log.d
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tbcacademy.BaseFragment
import com.example.tbcacademy.databinding.FragmentUploadImageBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UploadImageFragment : BaseFragment<FragmentUploadImageBinding>(FragmentUploadImageBinding::inflate) {
    private val viewModel: UploadImageViewModel by viewModels()

    override fun start() {
        listeners()
        observeState()
        observeEffects()
    }

    private fun listeners(){
        binding.addImageButton.setOnClickListener {
            d("UploadImageFragment", "Add Image button clicked")
            showImagePicker()
        }
    }

    //shows bottom sheet fragment
    private fun showImagePicker(){
        val bottomSheet = PickImageBottomSheet { isCamera ->
            if (isCamera) {
                viewModel.obtainEvent(UploadImageEvent.OpenCamera)
                openCamera()
            } else {
                viewModel.obtainEvent(UploadImageEvent.OpenGallery)
                openGallery()
            }
        }
        bottomSheet.show(parentFragmentManager, "PickImageBottomSheet")
    }

    //observes state
    private fun observeState(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collectLatest { state ->
                when (state) {
                    is UploadImageState.Loading -> showLoading()
                    is UploadImageState.ImageLoaded -> {
                        hideLoading()
                        binding.uploadedImage.setImageBitmap(state.bitmap)
                    }
                    is UploadImageState.UriLoaded -> {
                        hideLoading()
                        binding.uploadedImage.setImageURI(state.uri)
                        binding.imageText.visibility = View.VISIBLE
                    }
                    is UploadImageState.Error -> {
                        hideLoading()
                        showError(state.message)
                    }
                    else -> hideLoading()
                }
            }
        }
    }

    //observes effect
    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    is UploadImageEffect.ShowError -> showError(effect.message)
                }
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    //handles camera and its intent
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                viewModel.obtainEvent(UploadImageEvent.ImageCaptured(intent))
            } ?: showError("Failed to capture image")
        }
    }

    //handles gallery and its intent
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                viewModel.obtainEvent(UploadImageEvent.ImageSelected(intent))
            } ?: showError("No image selected")
        }
    }

    private fun showLoading(){
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loader.visibility = View.GONE
    }

    private fun showError(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

}