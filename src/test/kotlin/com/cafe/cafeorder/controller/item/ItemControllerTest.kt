package com.cafe.cafeorder.controller.item

import com.cafe.cafeorder.controller.item.dto.ItemCreateResponse
import com.cafe.cafeorder.fixtures.BaseSpringBootTest
import com.cafe.cafeorder.fixtures.item.ItemRequestFixtures
import com.cafe.cafeorder.repository.item.ItemRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

class ItemControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val itemRepository: ItemRepository
): BaseSpringBootTest() {

    @AfterEach
    fun tearDown() {
        itemRepository.deleteAll()
    }

    @Test
    fun `아이템 생성에 성공한다`() {
        // given
        val request = ItemRequestFixtures.createRequest("아메리카노", 2000)

        // when
        val response = mockMvc.post("/api/v1/items/item") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect { status { isOk() }
        }.andReturn().response.contentAsString

        // then
        val itemResponse = objectMapper.readValue(response, ItemCreateResponse::class.java)
        assertThat(itemResponse.name).isEqualTo(itemResponse.name)
        assertThat(itemResponse.price).isEqualTo(itemResponse.price)
    }
}