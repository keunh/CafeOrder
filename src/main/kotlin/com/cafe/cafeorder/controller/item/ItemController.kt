package com.cafe.cafeorder.controller.item

import com.cafe.cafeorder.controller.item.dto.ItemCreateRequest
import com.cafe.cafeorder.controller.item.dto.ItemCreateResponse
import com.cafe.cafeorder.service.item.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val itemService: ItemService
) {

    @PostMapping("/api/v1/items/item")
    fun createItem(@RequestBody request: ItemCreateRequest): ResponseEntity<ItemCreateResponse> {
        val response = itemService.createItem(request)
        return ResponseEntity.ok(response)
    }
}