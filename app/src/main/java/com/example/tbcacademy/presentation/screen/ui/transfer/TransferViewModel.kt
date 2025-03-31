package com.example.tbcacademy.presentation.screen.ui.transfer

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.BaseViewModel
import com.example.tbcacademy.domain.usecase.GetCardsUseCase
import com.example.tbcacademy.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
): BaseViewModel<TransferState, TransferEvent, TransferEffect>(initialState = TransferState()) {
    override fun obtainEvent(event: TransferEvent) {
        when (event) {
            is TransferEvent.LoadCards -> loadCards()

            is TransferEvent.FromCardClicked -> viewModelScope.launch {
                emitEffect(TransferEffect.ClickFromBottomSheet)
            }

            is TransferEvent.ToCardClicked -> viewModelScope.launch {
                emitEffect(TransferEffect.ClickToBottomSheet)
            }

            is TransferEvent.OnCardSelected -> {
                updateState { copy(selectedFromCard = event.card) }
            }

            is TransferEvent.OnToCardSelected -> {
                updateState { copy(selectedToCard = event.card) }

                val fromCurrency = viewState.value.selectedFromCard?.cardType
                val toCurrency = event.card.cardType

                if (fromCurrency != null && toCurrency != null && fromCurrency != toCurrency) {
                    obtainEvent(TransferEvent.LoadExchangeRate(fromCurrency, toCurrency))
                }
            }

            is TransferEvent.LoadExchangeRate -> {

            }
        }
    }

    private fun loadCards() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }

            runCatching { getCardsUseCase() }
                .onSuccess { cards ->
                    updateState {
                        copy(
                            isLoading = false,
                            cards = cards.map { it.toPresentation() }
                        )
                    }
                }
                .onFailure { error ->
                    updateState { copy(isLoading = false, error = error.message) }
                    emitEffect(TransferEffect.ShowError(error.message ?: "Unknown error"))
                }
        }
    }
}