package com.cafe.cafeorder.controller.order.dto

import com.cafe.cafeorder.repository.item.entity.ItemEntity

data class OrderCreateRequest(
    val ordersItems: List<OrderItemRequest>
) {

    fun getTotalPrice(itemEntities: List<ItemEntity>): Int {
        val itemMap = itemEntities.associateBy { it.id }

        return ordersItems.sumOf { itemMap[it.itemId]!!.price * it.quantity }
    }
}