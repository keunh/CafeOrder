package com.cafe.cafeorder.controller.item.dto

import com.cafe.cafeorder.repository.item.entity.ItemEntity

data class ItemCreateResponse(
    val id: Long,
    val name: String,
    val price: Int,
) {
    companion object {
        fun fromEntity(itemEntity: ItemEntity): ItemCreateResponse {
            return with(itemEntity) {
                return ItemCreateResponse(
                    id = id,
                    name = name,
                    price = price,
                )
            }
        }
    }
}