package com.cafe.cafeorder.service.item

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.common.exception.GlobalException
import com.cafe.cafeorder.controller.item.dto.ItemCreateRequest
import com.cafe.cafeorder.controller.item.dto.ItemCreateResponse
import com.cafe.cafeorder.repository.item.ItemRepository
import com.cafe.cafeorder.repository.item.entity.ItemEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService (
    private val itemRepository: ItemRepository
) {

    @Transactional
    fun createItem(request: ItemCreateRequest): ItemCreateResponse {
        val findByName = itemRepository.findByName(request.name)
        if (findByName != null) {
            throw GlobalException(ErrorCode.ALREADY_SAVED_ITEM)
        }

        val itemEntity = itemRepository.save(request.toEntity())
        return ItemCreateResponse.fromEntity(itemEntity)
    }

    @Transactional(readOnly = true)
    fun validateAndFindByItemIds(itemIds: List<Long>): List<ItemEntity> {
        val items = itemRepository.findByIdIn(itemIds)
        val findItemIds = items.map { it.id }.toSet()

        val cannotFoundItem = itemIds.filterNot { it in findItemIds }
        if (cannotFoundItem.isNotEmpty()) {
            throw GlobalException(ErrorCode.CANNOT_FIND_ITEM)
        }

        return itemRepository.findByIdIn(itemIds)
    }
}