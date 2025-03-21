package com.example.tbcacademy.domain.usecases

import com.example.tbcacademy.data.model.Resource
import com.example.tbcacademy.data.model.toDomainItem
import com.example.tbcacademy.data.repository.ItemRepository
import com.example.tbcacademy.domain.model.DomainItem
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {

    //Fetches and processes items from the repository
    suspend fun execute(query: String = ""): List<DomainItem> {
        val response = repository.getItems()

        return when (response) {
            is Resource.Success -> {
                val allItems = response.data.map{it.toDomainItem()}
                val flattenedItems = allItems.flatMap { flattenItems(it) }

                val filteredItems = if (query.isBlank()) {
                    flattenedItems
                } else {
                    flattenedItems.filter { it.name.contains(query, ignoreCase = true) }
                }

                filteredItems
            }

            else -> emptyList()
        }
    }

    //Flattens hierarchical data structure into a list
    private fun flattenItems(item: DomainItem): List<DomainItem> {
        return listOf(item) + item.children.flatMap { flattenItems(it) }
    }
}