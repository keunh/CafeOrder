package com.cafe.cafeorder.adapter.dto

class PaymentCancelResponse(
    val paymentId: String,
    val amount: Int = 0,
    val result: String
) {
    companion object {
        fun fail(paymentId: String): PaymentCancelResponse {
            return PaymentCancelResponse(
                paymentId = paymentId,
                result = "cancel_fail"
            )
        }

        fun success(paymentId: String, amount: Int): PaymentCancelResponse {
            return PaymentCancelResponse(
                paymentId = paymentId,
                amount = amount,
                result = "cancel_success"
            )
        }
    }
}