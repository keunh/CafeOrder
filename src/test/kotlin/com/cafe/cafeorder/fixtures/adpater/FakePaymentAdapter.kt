package com.cafe.cafeorder.fixtures.adpater

import com.cafe.cafeorder.adapter.PaymentAdapter
import com.cafe.cafeorder.adapter.dto.PaymentCancelRequest
import com.cafe.cafeorder.adapter.dto.PaymentCancelResponse
import com.cafe.cafeorder.adapter.dto.PaymentRequest
import com.cafe.cafeorder.adapter.dto.PaymentResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class FakePaymentAdapter: PaymentAdapter {

    override fun requestPayment(paymentRequest: PaymentRequest): PaymentResponse {
        return PaymentResponse.success(paymentRequest.amount)
    }

    override fun cancelPayment(cancelRequest: PaymentCancelRequest): PaymentCancelResponse {
        return PaymentCancelResponse.success(cancelRequest.paymentId, cancelRequest.amount)
    }
}