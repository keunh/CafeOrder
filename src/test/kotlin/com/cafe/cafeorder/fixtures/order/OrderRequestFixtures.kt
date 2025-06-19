package com.cafe.cafeorder.fixtures.order

import com.cafe.cafeorder.adapter.dto.PaymentCancelResponse
import com.cafe.cafeorder.controller.order.dto.OrderCreateRequest
import com.cafe.cafeorder.controller.order.dto.OrderItemRequest
import com.cafe.cafeorder.fixtures.adpater.PaymentAdapterFixtures
import com.cafe.cafeorder.service.order.dto.OrderCancelRequestDto
import com.cafe.cafeorder.service.order.dto.OrderCreateRequestDto
import com.cafe.cafeorder.service.order.dto.OrderItemCreateRequestDto

class OrderRequestFixtures {
    companion object {
        fun createOrderRequestWithItems(): OrderCreateRequest {
            val items = listOf(
                createOrderItemRequest(1, 2),
                createOrderItemRequest(2)
            )
            return OrderCreateRequest(items)
        }

        fun createOrderItemRequest(itemId: Long, quantity: Int = 1): OrderItemRequest {
            return OrderItemRequest(itemId, quantity)
        }

        fun createOrderItemRequestDto(): List<OrderItemCreateRequestDto> {
            return listOf(
                OrderItemCreateRequestDto(1, "아메리카노", 2000, 1),
                OrderItemCreateRequestDto(2, "카푸치노", 3000, 2),
            )
        }

        fun createSuccessOrderRequestDtoWithItem2(): OrderCreateRequestDto {
            return OrderCreateRequestDto(
                PaymentAdapterFixtures.paymentSuccess(),
                createOrderItemRequestDto()
            )
        }

        fun createFailOrderRequestDtoWithItem2(): OrderCreateRequestDto {
            return OrderCreateRequestDto(
                PaymentAdapterFixtures.paymentFail(),
                createOrderItemRequestDto()
            )
        }

        fun createCancelSuccessOrderRequestDto(): OrderCancelRequestDto {
            return OrderCancelRequestDto(
                PaymentCancelResponse.success("TEST_PAYMENT_ID", 0)
            )
        }

        fun createCancelFailOrderRequestDto(): OrderCancelRequestDto {
            return OrderCancelRequestDto(
                PaymentCancelResponse.fail("TEST_PAYMENT_ID")
            )
        }
    }
}