package com.example.tbcacademy.presentation.searchPage

import androidx.lifecycle.viewModelScope
import com.example.tbcacademy.BaseViewModel
import com.example.tbcacademy.data.model.Item
import com.example.tbcacademy.data.model.Resource
import com.example.tbcacademy.data.remote.HandleHttpRequests
import com.example.tbcacademy.domain.model.DomainItem
import com.example.tbcacademy.domain.model.toItem
import com.example.tbcacademy.domain.usecases.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale.Category
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemUseCase: GetItemUseCase
): BaseViewModel<ItemState, ItemEvent, ItemEffect>(ItemState.Loading) {
    override fun obtainEvent(event: ItemEvent) {
        when(event) {
            is ItemEvent.LoadItems -> fetchItems()
        }
    }

    init {
        obtainEvent(ItemEvent.LoadItems)
    }

    private fun fetchItems(){
        viewModelScope.launch {
            updateState { ItemState.Loading }

            val result: Resource<List<DomainItem>> = HandleHttpRequests.handleHttpRequest {
                getItemUseCase.execute()
            }

            when (result) {
                is Resource.Success -> {
                    updateState { ItemState.Success(result.data.map{it.toItem()}) }
                }
                is Resource.Error -> {
                    updateState { ItemState.Error(result.errorMessage) }
                    emitEffect(ItemEffect.ShowError(result.errorMessage))
                }

                else -> Unit
            }
        }
    }
}