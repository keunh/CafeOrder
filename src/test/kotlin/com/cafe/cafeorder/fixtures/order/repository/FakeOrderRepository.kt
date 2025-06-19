package com.cafe.cafeorder.fixtures.order.repository

import com.cafe.cafeorder.repository.order.OrderRepository
import com.cafe.cafeorder.repository.order.entity.OrderEntity
import java.util.concurrent.ConcurrentHashMap

class FakeOrderRepository: OrderRepository {

    companion object {
        var ID = 1L
        val ENTITY_MANAGER = ConcurrentHashMap<Long, OrderEntity>()
    }

    override fun findById(orderId: Long): OrderEntity? {
        return ENTITY_MANAGER[orderId]
    }

    override fun <T> save(entity: T): T {
        val orderEntity = entity as OrderEntity
        val copyEntity = with(orderEntity) {
            OrderEntity(
                id = ID,
                memberId = memberId,
                orderStatus = orderStatus,
                paymentId = paymentId,
                paymentResult = paymentResult,
                orderItems = orderItems
            )
        }
        ENTITY_MANAGER[ID] = copyEntity
        return copyEntity as T
    }

    override fun deleteAll() {
        ENTITY_MANAGER.clear()
    }
}