package com.example.tbcacademy.presentation.searchPage

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.BaseViewModel
import com.example.tbcacademy.domain.usecases.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase
): BaseViewModel<ItemState, ItemEvent, ItemEffect>(ItemState.Loading) {
    // Holds the current search coroutine to debounce input
    private var searchJob: Job? = null

    //Handles event
    override fun obtainEvent(event: ItemEvent) {
        when (event) {
            is ItemEvent.LoadItems -> fetchItems(event.query)
        }
    }

    //fetch items logic
    private fun fetchItems(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            updateState { ItemState.Loading }

            val items = getItemUseCase.execute(query)

            updateState { ItemState.Success(items) }
        }
    }
}