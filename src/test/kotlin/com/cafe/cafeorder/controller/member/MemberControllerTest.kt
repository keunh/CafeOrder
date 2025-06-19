package com.cafe.cafeorder.controller.member

import com.cafe.cafeorder.fixtures.BaseSpringBootTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

class MemberControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
): BaseSpringBootTest() {

    @Test
    fun createMember() {
    }

    @Test
    fun findMember() {
    }

    @Test
    fun withdrawalMember() {
    }

    @Test
    fun restoreMember() {
    }
}