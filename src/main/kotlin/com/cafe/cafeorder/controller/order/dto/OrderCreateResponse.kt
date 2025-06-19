package com.cafe.cafeorder.controller.order.dto

data class OrderCreateResponse(
    val orderId: Long,
    val totalPrice: Int
) {
}