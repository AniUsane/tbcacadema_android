package com.example.tbcacademy.data.model

import com.example.tbcacademy.domain.model.DomainItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val id: String,
    val name: String,
    @SerialName("name_de")
    val nameDe: String,
    val createdAt: String,
    @SerialName("bgl_number")
    val bglNumber: Int?,
    @SerialName("bgl_variant")
    val bglVariant: String?,
    @SerialName("order_id")
    val orderId: Int,
    val main: String?,
    val children: List<Item>

)

fun Item.toDomainItem(parentId: String? = null, depth: Int = 0): DomainItem {
    return DomainItem(
        id = id,
        name = name,
        parentId = parentId,
        depth =  depth,
        nameDe = nameDe,
        createdAt = createdAt,
        bglNumber = bglNumber,
        bglVariant = bglVariant,
        orderId = orderId,
        main = main,
        children = children.map{ it.toDomainItem(id, depth + 1)}
    )
}