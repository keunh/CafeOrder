package com.cafe.cafeorder.controller.order

import com.cafe.cafeorder.controller.item.dto.ItemCreateResponse
import com.cafe.cafeorder.fixtures.BaseSpringBootTest
import com.cafe.cafeorder.fixtures.item.ItemRequestFixtures
import com.cafe.cafeorder.repository.item.ItemRepository
import com.cafe.cafeorder.repository.order.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class OrderControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val orderRepository: OrderRepository,
    val itemRepository: ItemRepository
): BaseSpringBootTest() {

    @AfterEach
    fun tearDown() {
        itemRepository.deleteAll()
        orderRepository.deleteAll()
    }

    @Test
    fun `주문에 성공한다`() {

    }

    @Test
    fun `주문 취소에 성공한다`() {

    }
}
