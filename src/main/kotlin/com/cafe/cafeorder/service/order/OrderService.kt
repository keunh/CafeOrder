package com.cafe.cafeorder.service.order

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.common.exception.GlobalException
import com.cafe.cafeorder.repository.order.OrderRepository
import com.cafe.cafeorder.repository.order.entity.OrderEntity
import com.cafe.cafeorder.service.order.dto.OrderCancelRequestDto
import com.cafe.cafeorder.service.order.dto.OrderCreateRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    @Transactional
    fun createOrder(memberId: Long, orderCreateRequestDto: OrderCreateRequestDto): OrderEntity {
        val orderEntity = OrderEntity(
            memberId = memberId,
            orderStatus = orderCreateRequestDto.getPaymentStatus(),
            paymentId = orderCreateRequestDto.getPaymentId(),
            paymentResult = orderCreateRequestDto.getPaymentResult(),
        )
        val orderItems = orderCreateRequestDto.toOrderItemEntity(orderEntity)
        orderEntity.orderItems.addAll(orderItems)
        return orderRepository.save(orderEntity)
    }

    @Transactional(readOnly = true)
    fun validateAndFindCancelOrder(orderId: Long): OrderEntity {
        val orderEntity = findByOrderId(orderId)
        if (!orderEntity.canCancel()) {
            throw GlobalException(ErrorCode.CANNOT_CANCEL_ORDER_STATUS)
        }
        orderEntity.getTotalPrice()
        return orderEntity
    }

    private fun findByOrderId(orderId: Long): OrderEntity {
        return orderRepository.findById(orderId)
            ?: throw GlobalException(ErrorCode.CANNOT_FIND_ORDER)
    }

    @Transactional
    fun cancelOrder(orderId: Long, cancelDto: OrderCancelRequestDto): OrderEntity {
        val orderEntity = findByOrderId(orderId)
        orderEntity.cancel(cancelDto.getOrderStatus(), cancelDto.getCancelResult())
        return orderEntity
    }
}