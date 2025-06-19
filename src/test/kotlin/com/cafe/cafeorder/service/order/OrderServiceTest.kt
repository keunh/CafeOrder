package com.cafe.cafeorder.service.order

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.fixtures.order.OrderRequestFixtures
import com.cafe.cafeorder.fixtures.order.repository.FakeOrderRepository
import com.cafe.cafeorder.repository.order.constant.OrderStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class OrderServiceTest {

    private val orderRepository = FakeOrderRepository()
    private val orderService = OrderService(orderRepository)
    private val TEST_MEMBER_ID = 1234L

    @AfterEach
    fun tearDown() {
        orderRepository.deleteAll()
    }

    @Test
    fun `오더 생성에 성공한다1`() {
        // given
        val request = OrderRequestFixtures.createSuccessOrderRequestDtoWithItem2()

        // when
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // then
        assertThat(order.memberId).isEqualTo(TEST_MEMBER_ID)
        assertThat(order.orderStatus).isEqualTo(OrderStatus.PAYMENT_SUCCESS)
        assertThat(order.orderItems.size).isEqualTo(2)

        val requestOrderItem = request.orderItems[0]
        val orderItem = order.orderItems[0]
        assertThat(orderItem.name).isEqualTo(requestOrderItem.name)
        assertThat(orderItem.price).isEqualTo(requestOrderItem.price)
        assertThat(orderItem.quantity).isEqualTo(requestOrderItem.quantity)
    }

    @Test
    fun `오더 생성에 성공한다2`() {
        // given
        val request = OrderRequestFixtures.createFailOrderRequestDtoWithItem2()

        // when
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // then
        assertThat(order.memberId).isEqualTo(TEST_MEMBER_ID)
        assertThat(order.orderStatus).isEqualTo(OrderStatus.PAYMENT_FAIL)
        assertThat(order.orderItems.size).isEqualTo(2)

        val requestOrderItem = request.orderItems[0]
        val orderItem = order.orderItems[0]
        assertThat(orderItem.name).isEqualTo(requestOrderItem.name)
        assertThat(orderItem.price).isEqualTo(requestOrderItem.price)
        assertThat(orderItem.quantity).isEqualTo(requestOrderItem.quantity)
    }

    @Test
    fun `오더 취소 유효성 검증에 성공한다`() {
        // given
        val request = OrderRequestFixtures.createSuccessOrderRequestDtoWithItem2()
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // when
        val orderEntity = orderService.validateAndFindCancelOrder(order.id)

        // then
        assertThat(orderEntity.id).isEqualTo(order.id)
    }

    @Test
    fun `오더 취소 유효성 검증에 실패한다`() {
        // given
        val request = OrderRequestFixtures.createFailOrderRequestDtoWithItem2()
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // when, then
        assertThatThrownBy {
            orderService.validateAndFindCancelOrder(order.id)
        }.hasMessageContaining(ErrorCode.CANNOT_CANCEL_ORDER_STATUS.message)
    }

    @Test
    fun `오더 취소에 성공한다`() {
        // given
        val request = OrderRequestFixtures.createSuccessOrderRequestDtoWithItem2()
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // when
        orderService.cancelOrder(order.id, OrderRequestFixtures.createCancelSuccessOrderRequestDto())

        // then
        assertThat(order.memberId).isEqualTo(TEST_MEMBER_ID)
        assertThat(order.orderStatus).isEqualTo(OrderStatus.CANCEL_SUCCESS)
    }

    @Test
    fun `오더 취소에 실패한다`() {
        // given
        val request = OrderRequestFixtures.createSuccessOrderRequestDtoWithItem2()
        val order = orderService.createOrder(TEST_MEMBER_ID, request)

        // when
        orderService.cancelOrder(order.id, OrderRequestFixtures.createCancelFailOrderRequestDto())

        // then
        assertThat(order.memberId).isEqualTo(TEST_MEMBER_ID)
        assertThat(order.orderStatus).isEqualTo(OrderStatus.CANCEL_FAIL)
    }
}