package com.cafe.cafeorder.repository.order.entity

import com.cafe.cafeorder.repository.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "ordersitem")
class OrderItemEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: OrderEntity,

    @Column(nullable = false, length = 30)
    val itemId: Long,

    @Column(nullable = false, length = 30)
    val name: String,

    @Column(nullable = false, length = 10)
    val price: Int,

    @Column(nullable = false, length = 10)
    val quantity: Int,

    ): BaseEntity()