package com.cafe.cafeorder.fixtures.adpater

import com.cafe.cafeorder.adapter.dto.PaymentResponse

class PaymentAdapterFixtures {
    companion object {
        fun paymentSuccess(amount: Int = 1000): PaymentResponse {
            return PaymentResponse.success(amount)
        }

        fun paymentFail(): PaymentResponse {
            return PaymentResponse.fail()
        }
    }
}