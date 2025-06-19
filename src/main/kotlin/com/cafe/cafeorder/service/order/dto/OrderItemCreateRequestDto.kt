package com.cafe.cafeorder.service.order.dto

import com.cafe.cafeorder.adapter.dto.PaymentResponse
import com.cafe.cafeorder.controller.order.dto.OrderItemRequest
import com.cafe.cafeorder.repository.item.entity.ItemEntity
import com.cafe.cafeorder.repository.order.constant.OrderStatus
import com.cafe.cafeorder.repository.order.entity.OrderEntity
import com.cafe.cafeorder.repository.order.entity.OrderItemEntity

class OrderCreateRequestDto(
    val paymentResponse: PaymentResponse,
    val orderItems: List<OrderItemCreateRequestDto>
) {

    companion object {
        fun of(paymentResponse: PaymentResponse,
               orderItems: List<OrderItemRequest>,
               itemEntities: List<ItemEntity>): OrderCreateRequestDto {

            val itemMap = itemEntities.associateBy { it.id }
            val orderItemsDto = orderItems.map {
                val item = itemMap[it.itemId]!!
                OrderItemCreateRequestDto(item.id, item.name, item.price, it.quantity)
            }

            return OrderCreateRequestDto(
                paymentResponse = paymentResponse,
                orderItems = orderItemsDto
            )
        }
    }

    fun getPaymentStatus(): OrderStatus {
        return when (this.paymentResponse.result) {
            "success" -> OrderStatus.PAYMENT_SUCCESS
            else -> OrderStatus.PAYMENT_FAIL
        }
    }

    fun getPaymentId(): String? {
        return this.paymentResponse.paymentId
    }

    fun getPaymentResult(): String {
        return this.paymentResponse.result
    }

    fun toOrderItemEntity(orderEntity: OrderEntity): List<OrderItemEntity> {
        return this.orderItems.map { it.toEntity(orderEntity) }
    }
}

class OrderItemCreateRequestDto(
    val itemId: Long,
    val name: String,
    val price: Int,
    val quantity: Int
) {

    fun toEntity(orderEntity: OrderEntity): OrderItemEntity {
        return OrderItemEntity(
            order = orderEntity,
            itemId = this.itemId,
            name = this.name,
            price = this.price,
            quantity = this.quantity
        )
    }
}