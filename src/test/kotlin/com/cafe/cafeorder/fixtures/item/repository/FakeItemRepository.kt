package com.cafe.cafeorder.fixtures.item.repository

import com.cafe.cafeorder.repository.item.ItemRepository
import com.cafe.cafeorder.repository.item.entity.ItemEntity
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item
import java.util.concurrent.ConcurrentHashMap

class FakeItemRepository: ItemRepository {

    companion object {
        var ID = 1L
        val ENTITY_MANAGER = ConcurrentHashMap<Long, ItemEntity>()
    }

    override fun findByName(name: String): ItemEntity? {
        return ENTITY_MANAGER.values.firstOrNull { it.name == name }
    }

    override fun findByIdIn(ids: List<Long>): List<ItemEntity> {
        return ENTITY_MANAGER.values.filter { ids.contains(it.id) }
    }

    override fun <T> save(entity: T): T {
        val itemEntity = entity as ItemEntity
        val copyEntity = with(itemEntity) {
            ItemEntity(id = ID, name = name, price = price, imageUrl = imageUrl)
        }
        ENTITY_MANAGER[ID++] = copyEntity
        return copyEntity as T
    }

    override fun deleteAll() {
        ENTITY_MANAGER.clear()
    }
}