package com.cafe.cafeorder.controller.item.dto

import com.cafe.cafeorder.repository.item.entity.ItemEntity

data class ItemCreateRequest(
    val name: String,
    val price: Int,
    val imageUrl: String?
) {
    fun toEntity(): ItemEntity {
        return ItemEntity(
            name = name,
            price = price,
            imageUrl = imageUrl
        )
    }
}