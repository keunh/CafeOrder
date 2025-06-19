package com.cafe.cafeorder.adapter.dto

class PaymentRequest(
    val memberId: String,
    val amount: Int
) {
    companion object {
        fun of(memberId: String, amount: Int): PaymentRequest {
            return PaymentRequest(memberId, amount)
        }
    }
}
