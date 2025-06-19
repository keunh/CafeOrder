package com.cafe.cafeorder.controller.order.dto

data class OrderItemRequest(
    val itemId: Long,
    val quantity: Int,
) {
}
