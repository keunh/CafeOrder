package com.cafe.cafeorder.application

import com.cafe.cafeorder.adapter.PaymentAdapter
import com.cafe.cafeorder.adapter.dto.PaymentCancelRequest
import com.cafe.cafeorder.adapter.dto.PaymentCancelResponse
import com.cafe.cafeorder.adapter.dto.PaymentRequest
import com.cafe.cafeorder.adapter.dto.PaymentResponse
import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.common.exception.GlobalException
import com.cafe.cafeorder.common.utils.Log
import com.cafe.cafeorder.controller.order.dto.OrderCancelResponse
import com.cafe.cafeorder.controller.order.dto.OrderCreateRequest
import com.cafe.cafeorder.controller.order.dto.OrderCreateResponse
import com.cafe.cafeorder.repository.item.entity.ItemEntity
import com.cafe.cafeorder.repository.order.entity.OrderEntity
import com.cafe.cafeorder.service.item.ItemService
import com.cafe.cafeorder.service.member.MemberService
import com.cafe.cafeorder.service.order.OrderService
import com.cafe.cafeorder.service.order.dto.OrderCancelRequestDto
import com.cafe.cafeorder.service.order.dto.OrderCreateRequestDto
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class OrderUseCaseService(
    private val memberService: MemberService,
    private val paymentAdapter: PaymentAdapter,
    private val itemService: ItemService,
    private val orderService: OrderService,
) {

    companion object: Log

    fun order(memberId: Long, request: OrderCreateRequest): OrderCreateResponse {
        memberService.validateMember(memberId)
        val foundItems = itemService.validateAndFindByItemIds(request.ordersItems.map { it.itemId })

        val totalPrice = request.getTotalPrice(foundItems)
        val paymentRequest = PaymentRequest.of(memberId.toString(), totalPrice)
        val paymentResponse = paymentAdapter.requestPayment(paymentRequest)

        val orderEntity = saveOrder(paymentResponse, request, foundItems, memberId, totalPrice)
        if (orderEntity.orderStatus.isFail()) {
            throw GlobalException(ErrorCode.FAIL_ORDER)
        }
        return OrderCreateResponse(orderEntity.id, totalPrice)
    }

    private fun saveOrder(
        paymentResponse: PaymentResponse,
        request: OrderCreateRequest,
        foundItems: List<ItemEntity>,
        memberId: Long,
        totalPrice: Int
    ): OrderEntity {
        try {
            val orderCreateRequest = OrderCreateRequestDto.of(paymentResponse, request.ordersItems, foundItems)
            return orderService.createOrder(memberId, orderCreateRequest)
        } catch (e: Exception) {
            if (paymentResponse.canCancel()) {
                cancelPaymentByOrderFail(memberId, paymentResponse, totalPrice)
            }
            throw GlobalException(ErrorCode.FAIL_ORDER, e)
        }
    }

    private fun cancelPaymentByOrderFail(
        memberId: Long,
        paymentResponse: PaymentResponse,
        totalPrice: Int
    ): PaymentCancelResponse {
        val paymentCancelRequest = PaymentCancelRequest.of(memberId, paymentResponse.paymentId, totalPrice)
        return paymentAdapter.cancelPayment(paymentCancelRequest)
    }

    fun cancelOrder(memberId: Long, orderId: Long): OrderCancelResponse {
        memberService.validateMember(memberId)
        val order = orderService.validateAndFindCancelOrder(orderId)

        val paymentCancelRequest = PaymentCancelRequest.of(memberId, order.paymentId!!, order.getTotalPrice())
        val cancelResponse = paymentAdapter.cancelPayment(paymentCancelRequest)

        val orderEntity = orderService.cancelOrder(orderId, OrderCancelRequestDto(cancelResponse))
        if (orderEntity.orderStatus.isFail()) {
            throw GlobalException(ErrorCode.FAIL_CANCEL_ORDER)
        }
        return OrderCancelResponse(orderId)
    }
}