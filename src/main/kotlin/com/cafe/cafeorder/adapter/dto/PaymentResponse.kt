package com.cafe.cafeorder.adapter.dto

class PaymentResponse(
    val paymentId: String,
    val amount: Int = 0,
    val result: String
) {
    companion object {
        fun success(amount: Int): PaymentResponse {
            return PaymentResponse(
                paymentId = "TEST_PAYMENT_ID",
                amount = amount,
                result = "success"
            )
        }

        fun fail(): PaymentResponse {
            return PaymentResponse(
                paymentId = "",
                result = "fail"
            )
        }
    }

    fun canCancel(): Boolean {
        return result == "success"
    }
}
