package com.cafe.cafeorder.service.item

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.controller.item.dto.ItemCreateRequest
import com.cafe.cafeorder.fixtures.item.repository.FakeItemRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class ItemServiceTest {

    private val itemRepository = FakeItemRepository()
    private val itemService = ItemService(itemRepository)

    @AfterEach
    fun tearDown() {
        itemRepository.deleteAll()
    }

    @Test
    fun `아이템 생성에 성공한다`() {
        // given
        val request = ItemCreateRequest("아메리카노", 1000, "")

        // when
        val item = itemService.createItem(request)

        // then
        assertThat(item.name).isEqualTo(request.name)
        assertThat(item.price).isEqualTo(request.price)
    }

    @Test
    fun `아이템 생성에 실패한다 - 이름 중복`() {
        // given
        val request = ItemCreateRequest("아메리카노", 1000, "")
        val item = itemService.createItem(request)

        // when, then
        assertThatThrownBy {
            itemService.createItem(request)
        }.hasMessageContaining(ErrorCode.ALREADY_SAVED_ITEM.message)
    }

    @Test
    fun `아이템 유효성 검증에 성공한다`() {
        // given
        val request = ItemCreateRequest("아메리카노", 1000, "")
        val item = itemService.createItem(request)

        // when
        val items = itemService.validateAndFindByItemIds(listOf(item.id))

        // then
        assertThat(items[0].name).isEqualTo(request.name)
        assertThat(items[0].price).isEqualTo(request.price)
    }

    @Test
    fun `아이템 유효성 검증에 실패한다 - 아이템 없음`() {
        // given, when, then
        assertThatThrownBy {
            itemService.validateAndFindByItemIds(listOf(1, 2))
        }.hasMessageContaining(ErrorCode.CANNOT_FIND_ITEM.message)
    }
}