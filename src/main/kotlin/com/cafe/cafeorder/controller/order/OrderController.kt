package com.cafe.cafeorder.controller.order

import com.cafe.cafeorder.application.OrderUseCaseService
import com.cafe.cafeorder.controller.order.dto.OrderCancelResponse
import com.cafe.cafeorder.controller.order.dto.OrderCreateRequest
import com.cafe.cafeorder.controller.order.dto.OrderCreateResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderUseCaseService: OrderUseCaseService
) {

    @PostMapping("/api/v1/orders/order")
    fun order(
        @RequestHeader(name = "Member-Id", required = true) memberId: Long,
        @RequestBody request: OrderCreateRequest
    ): ResponseEntity<OrderCreateResponse> {
        val response = orderUseCaseService.order(memberId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/api/v1/orders/order/{orderId}/cancel")
    fun cancelOrder(
        @RequestHeader(name = "Member-Id", required = true) memberId: Long,
        @PathVariable orderId: Long
    ): ResponseEntity<OrderCancelResponse> {
        val response = orderUseCaseService.cancelOrder(memberId, orderId)
        return ResponseEntity.ok(response)
    }
}