package com.example.tbcacademy.domain.usecases

import com.example.tbcacademy.data.model.Item
import com.example.tbcacademy.data.model.Resource
import com.example.tbcacademy.data.model.toDomainItem
import com.example.tbcacademy.data.repository.ItemRepository
import com.example.tbcacademy.domain.model.DomainItem
import javax.inject.Inject

class GetItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(query: String = ""): List<DomainItem> {
        val response = repository.getItems()

        return when (response) {
            is Resource.Success<*> -> {
                val allItems = response.data as? List<Item> ?: emptyList()

                val filteredItems = if (query.isBlank()) {
                    allItems
                } else {
                    allItems.filter { it.name.contains(query, ignoreCase = true) }
                }

                filteredItems.map { item ->
                    item.toDomainItem()
                }
            }

            else -> emptyList()
        }
    }

    private fun calculateDepth(item: DomainItem, items: List<DomainItem>): Int {
        var depth = 0
        var currentItem: DomainItem? = item

        while(currentItem?.parentId != null && depth < 4){
            currentItem = items.find{it.id == currentItem?.parentId}
            depth++
        }

        return depth
    }
}