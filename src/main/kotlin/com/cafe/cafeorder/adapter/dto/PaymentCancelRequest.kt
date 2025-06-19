package com.cafe.cafeorder.adapter.dto

class PaymentCancelRequest(
    val memberId: Long,
    val paymentId: String,
    val amount: Int = 0,
) {
    companion object {
        fun of(memberId: Long, paymentId: String, amount: Int): PaymentCancelRequest {
            return PaymentCancelRequest(memberId, paymentId, amount)
        }
    }
}
