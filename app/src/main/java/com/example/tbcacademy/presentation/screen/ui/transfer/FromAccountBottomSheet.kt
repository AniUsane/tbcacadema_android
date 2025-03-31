package com.example.tbcacademy.presentation.screen.ui.transfer

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.BaseBottomSheet
import com.example.tbcacademy.R
import com.example.tbcacademy.databinding.FragmentFromAccountBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FromAccountBottomSheet : BaseBottomSheet<FragmentFromAccountBottomSheetBinding>(
    FragmentFromAccountBottomSheetBinding::inflate
) {
    private val viewModel: TransferViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: CardAdapter

    override fun start() {
        viewModel.obtainEvent(TransferEvent.LoadCards)
        setupRecycler()
        observeState()
    }

    private fun setupRecycler() {
        adapter = CardAdapter { selectedCard ->
            viewModel.obtainEvent(TransferEvent.OnCardSelected(selectedCard))
            dismiss()
        }

        binding.accountRecyclerView.adapter = adapter
        binding.accountRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                adapter.submitList(state.cards)
            }
        }
    }

}