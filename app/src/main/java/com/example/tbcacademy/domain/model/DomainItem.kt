package com.example.tbcacademy.domain.model

import com.example.tbcacademy.data.model.Item

data class DomainItem(
    val id: String,
    val name: String,
    val parentId: String?,
    val depth: Int = 0,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: Int?,
    val bglVariant: String?,
    val orderId: Int,
    val main: String?,
    val children: List<DomainItem> = emptyList()
)

fun DomainItem.toItem(): Item {
    return Item(
        id = id,
        name = name,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        main = main,
        children = children.map{it.toItem()}
    )
}