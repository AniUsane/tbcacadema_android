package com.example.tbcacademy.presentation.searchPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tbcacademy.BaseFragment
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentItemBinding


class ItemFragment : BaseFragment<FragmentItemBinding>(FragmentItemBinding::inflate) {
    private val viewModel: ItemViewModel by viewModels()

    override fun start() {
        TODO("Not yet implemented")
    }


}