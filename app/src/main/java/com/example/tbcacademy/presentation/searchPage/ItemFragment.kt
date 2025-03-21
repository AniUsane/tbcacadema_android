package com.example.tbcacademy.presentation.searchPage

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.BaseFragment
import com.example.tbcacademy.databinding.FragmentItemBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemFragment : BaseFragment<FragmentItemBinding>(FragmentItemBinding::inflate) {
    private val viewModel: ItemViewModel by viewModels()
    private val adapter = ItemAdapter()

    override fun start() {
        setupRecyclerView()
        setupObservers()
        setupSearch()

        viewModel.obtainEvent(ItemEvent.LoadItems(""))
    }

    //Sets up recycler view
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    //Observes view model state and effect and updates UI
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collectLatest { state ->
                when (state) {
                    is ItemState.Loading -> showLoading()
                    is ItemState.Success -> {
                        hideLoading()
                        adapter.submitList(state.items)
                    }

                    is ItemState.Error -> showError(state.message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    is ItemEffect.ShowError -> showError(effect.message)
                }
            }
        }
    }

    //Handles text input search
    private fun setupSearch() {
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.obtainEvent(ItemEvent.LoadItems(s.toString()))
            }
        })
    }

    private fun showLoading() {
        binding.loader.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loader.visibility = View.GONE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}