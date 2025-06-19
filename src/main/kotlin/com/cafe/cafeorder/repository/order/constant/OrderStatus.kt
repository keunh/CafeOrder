package com.cafe.cafeorder.repository.order.constant

enum class OrderStatus {
    PAYMENT_SUCCESS,
    PAYMENT_FAIL,
    CANCEL_SUCCESS,
    CANCEL_FAIL
    ;

    fun isFail(): Boolean {
        return when(this) {
            PAYMENT_SUCCESS, CANCEL_SUCCESS -> false
            else -> true
        }
    }
}