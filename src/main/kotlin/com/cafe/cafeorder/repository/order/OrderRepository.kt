package com.cafe.cafeorder.repository.order

import com.cafe.cafeorder.repository.order.entity.OrderEntity
import com.example.kakaopayad.repository.common.BaseRepository

interface OrderRepository: BaseRepository<OrderEntity, Long> {

    fun findById(orderId: Long): OrderEntity?

    fun deleteAll()
}