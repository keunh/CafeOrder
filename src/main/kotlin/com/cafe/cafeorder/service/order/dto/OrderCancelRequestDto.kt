package com.cafe.cafeorder.service.order.dto

import com.cafe.cafeorder.adapter.dto.PaymentCancelResponse
import com.cafe.cafeorder.repository.order.constant.OrderStatus

class OrderCancelRequestDto(
    val paymentCancelResponse: PaymentCancelResponse,
) {
    fun getOrderStatus(): OrderStatus {
        if (paymentCancelResponse.result == "cancel_success") {
            return OrderStatus.CANCEL_SUCCESS
        }
        return OrderStatus.CANCEL_FAIL
    }

    fun getCancelResult(): String? {
        return paymentCancelResponse.result
    }
}