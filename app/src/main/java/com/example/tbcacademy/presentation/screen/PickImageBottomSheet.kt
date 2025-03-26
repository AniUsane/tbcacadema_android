package com.example.tbcacademy.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.tbcacademy.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PickImageBottomSheet(private val onImageSelected: (Boolean) -> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pick_image_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //handles taking image button click and returns true which selects camera
        view.findViewById<AppCompatButton>(R.id.takeImageBtn).setOnClickListener {
            onImageSelected(true)
            dismiss()
        }

        //handles choose image and opens gallery
        view.findViewById<AppCompatButton>(R.id.chooseImageBtn).setOnClickListener {
            onImageSelected(false)
            dismiss()
        }
    }
}