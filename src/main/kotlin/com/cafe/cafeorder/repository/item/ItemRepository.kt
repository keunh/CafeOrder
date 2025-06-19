package com.cafe.cafeorder.repository.item

import com.cafe.cafeorder.repository.item.entity.ItemEntity
import com.example.kakaopayad.repository.common.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository: BaseRepository<ItemEntity, Long> {

    fun findByName(name: String): ItemEntity?

    fun findByIdIn(ids: List<Long>): List<ItemEntity>

    fun deleteAll()
}