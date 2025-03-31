package com.example.tbcacademy.presentation.screen.ui.transfer

import android.util.Log.d
import android.util.Log.e
import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.BaseViewModel
import com.example.tbcacademy.domain.usecase.AccountValidationUseCase
import com.example.tbcacademy.domain.usecase.GetToAccountUseCase
import com.example.tbcacademy.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToAccountBottomSheetViewModel @Inject constructor(
    private val validateUseCase: AccountValidationUseCase,
    private val getToAccountUseCase: GetToAccountUseCase,
): BaseViewModel<ToAccountState, ToAccountEvent, ToAccountEffect>(initialState = ToAccountState()) {

    override fun obtainEvent(event: ToAccountEvent) {
        when (event) {
            is ToAccountEvent.TypeSelected -> {
                d("BottomSheet", "Selected Type: ${event.type}")
                updateState {
                    copy(selectedType = event.type, error = null)
                }
            }

            is ToAccountEvent.InputChanged -> {
                d("BottomSheet", "Input changed: ${event.input}")
                updateState {
                    copy(input = event.input, error = null)
                }
            }

            is ToAccountEvent.ContinueClicked -> {
                d("BottomSheet", "Continue clicked with input: ${viewState.value.input}, type: ${viewState.value.selectedType}")
                val result = validateUseCase(
                    viewState.value.selectedType,
                    viewState.value.input
                )

                when (result) {
                    is AccountValidationUseCase.ValidationResult.Valid -> {
                        d("BottomSheet", "Validation passed. Fetching account...")
                        viewModelScope.launch {
                            try {
                                val account = getToAccountUseCase(viewState.value.input)
                                d("BottomSheet", "Fetched account: ${account.accountNumber}")
                                emitEffect(ToAccountEffect.SubmitSuccess(account.toPresentation()))
                            } catch (e: Exception) {
                                e("BottomSheet", "Account fetch failed: ${e.message}")
                                emitEffect(ToAccountEffect.ShowError("Account not found."))
                            }
                        }
                    }

                    is AccountValidationUseCase.ValidationResult.Invalid -> {
                        e("BottomSheet", "Validation failed: ${result.message}")
                        updateState { copy(error = result.message) }
                        viewModelScope.launch {
                            emitEffect(ToAccountEffect.ShowError(result.message))
                        }
                    }
                }
            }
        }
    }
}