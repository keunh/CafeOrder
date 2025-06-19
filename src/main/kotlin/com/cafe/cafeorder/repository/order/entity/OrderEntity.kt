package com.cafe.cafeorder.repository.order.entity

import com.cafe.cafeorder.repository.entity.BaseEntity
import com.cafe.cafeorder.repository.order.constant.OrderStatus
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 30)
    val memberId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    var orderStatus: OrderStatus,

    @Column(nullable = false, length = 30)
    val paymentId: String? = null,

    @Column(nullable = false, length = 30)
    var paymentResult: String? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderItems: MutableList<OrderItemEntity> = mutableListOf()

): BaseEntity() {

    fun canCancel(): Boolean {
        return this.orderStatus == OrderStatus.PAYMENT_SUCCESS
                && this.paymentId != null
    }

    fun getTotalPrice(): Int {
        return this.orderItems.sumOf { it.price * it.quantity }
    }

    fun cancel(orderStatus: OrderStatus, paymentResult: String?) {
        this.orderStatus = orderStatus
        this.paymentResult = paymentResult
    }
}