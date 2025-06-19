package com.cafe.cafeorder.controller.member.dto

import com.cafe.cafeorder.repository.member.constants.Gender
import com.cafe.cafeorder.repository.member.entity.MemberEntity

data class MemberCreateRequest(
    val loginId: String,
    val name: String,
    val phoneNumber: String,
    val gender: Gender,
    val birth: String
) {

    fun toEntity(): MemberEntity {
        return MemberEntity(
            loginId = loginId,
            name = name,
            phoneNumber = phoneNumber,
            gender = gender,
            birth = birth
        )
    }
}