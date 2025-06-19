package com.cafe.cafeorder.fixtures.item

import com.cafe.cafeorder.controller.item.dto.ItemCreateRequest

class ItemRequestFixtures {
    companion object {
        fun createRequest(name: String, price: Int): ItemCreateRequest {
            return ItemCreateRequest(
                name = name,
                price = price,
                imageUrl = ""
            )
        }
    }
}