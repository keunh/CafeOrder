package com.cafe.cafeorder.adapter

import com.cafe.cafeorder.adapter.dto.PaymentCancelRequest
import com.cafe.cafeorder.adapter.dto.PaymentCancelResponse
import com.cafe.cafeorder.adapter.dto.PaymentRequest
import com.cafe.cafeorder.adapter.dto.PaymentResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import kotlin.random.Random

interface PaymentAdapter {
    fun requestPayment(paymentRequest: PaymentRequest): PaymentResponse
    fun cancelPayment(cancelRequest: PaymentCancelRequest): PaymentCancelResponse
}

@Component
@Profile("!test")
class MockPaymentAdapter: PaymentAdapter {

    override fun requestPayment(paymentRequest: PaymentRequest): PaymentResponse {
        Thread.sleep((Math.random() * 1000).toLong())

        if (Random.nextInt() % 100 == 1) {
            return PaymentResponse.fail()
        }
        return PaymentResponse.success(
            amount = paymentRequest.amount,
        )
    }

    override fun cancelPayment(cancelRequest: PaymentCancelRequest): PaymentCancelResponse {
        Thread.sleep((Math.random() * 1000).toLong())

        if (Random.nextInt() % 100 == 1) {
            return PaymentCancelResponse.fail(cancelRequest.paymentId)
        }
        return PaymentCancelResponse.success(
            paymentId = cancelRequest.paymentId,
            amount = cancelRequest.amount
        )
    }
}