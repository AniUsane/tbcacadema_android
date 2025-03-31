package com.example.tbcacademy.presentation.screen.ui.transfer

import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tbcacademy.BaseBottomSheet
import com.example.tbcacademy.databinding.FragmentToAccountBottomSheetBinding
import com.example.tbcacademy.domain.usecase.AccountValidationUseCase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToAccountBottomSheet : BaseBottomSheet<FragmentToAccountBottomSheetBinding>(
    FragmentToAccountBottomSheetBinding::inflate) {

    private val viewModel: ToAccountBottomSheetViewModel by viewModels()

    override fun start() {
        listeners()
        observeState()
        observeEffects()
    }

    private fun listeners(){
        binding.accountNumberRadio.setOnClickListener {
            viewModel.obtainEvent(ToAccountEvent.TypeSelected(AccountValidationUseCase.AccountType.ACCOUNT_NUMBER))
        }
        binding.personalNumberRadio.setOnClickListener {
            viewModel.obtainEvent(ToAccountEvent.TypeSelected(AccountValidationUseCase.AccountType.PERSONAL_NUMBER))
        }
        binding.phoneNumberRadio.setOnClickListener {
            viewModel.obtainEvent(ToAccountEvent.TypeSelected(AccountValidationUseCase.AccountType.PHONE_NUMBER))
        }

        binding.inputField.doOnTextChanged { text, _, _, _ ->
            viewModel.obtainEvent(ToAccountEvent.InputChanged(text.toString()))
        }

        binding.continueButton.setOnClickListener {
            viewModel.obtainEvent(ToAccountEvent.ContinueClicked)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { state ->
                binding.inputField.error = state.error
            }
        }
    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is ToAccountEffect.ShowError -> {
                        Snackbar.make(binding.root, effect.message, Snackbar.LENGTH_SHORT).show()
                    }

                    is ToAccountEffect.SubmitSuccess -> {
                        parentFragmentManager.setFragmentResult(
                            "to_account_result",
                            bundleOf("card" to effect.account)
                        )
                        dismiss()
                    }

                    is ToAccountEffect.SubmitToAccount -> {
                        parentFragmentManager.setFragmentResult(
                            "to_account_result",
                            bundleOf("card" to effect.card)
                        )
                        dismiss()
                    }
                }
            }
        }
    }
}