package com.cafe.cafeorder.application

import com.cafe.cafeorder.adapter.PaymentAdapter
import com.cafe.cafeorder.fixtures.order.OrderRequestFixtures
import com.cafe.cafeorder.service.item.ItemService
import com.cafe.cafeorder.service.member.MemberService
import com.cafe.cafeorder.service.order.OrderService
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OrderUseCaseServiceTest {

    private val memberService = mockk<MemberService>(relaxed = true, relaxUnitFun = true)
    private val paymentAdapter = mockk<PaymentAdapter>(relaxed = true, relaxUnitFun = true)
    private val itemService = mockk<ItemService>(relaxed = true, relaxUnitFun = true)
    private val orderService = mockk<OrderService>(relaxed = true, relaxUnitFun = true)
    private val orderUseCaseService = OrderUseCaseService(
        memberService, paymentAdapter, itemService, orderService
    )

    private val TEST_MEMBER_ID = 111L

    @Test
    fun `주문에 성공한다`() {
        // given
        val request = OrderRequestFixtures.createOrderRequestWithItems()

        // when
        // val response = orderUseCaseService.order(TEST_MEMBER_ID, request)

        // then
    }
}