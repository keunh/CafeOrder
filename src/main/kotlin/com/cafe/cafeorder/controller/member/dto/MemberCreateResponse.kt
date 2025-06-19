package com.cafe.cafeorder.controller.member.dto

import com.cafe.cafeorder.repository.member.constants.Gender
import com.cafe.cafeorder.repository.member.entity.MemberEntity

data class MemberCreateResponse(
    val memberId: Long,
    val loginId: String,
    val name: String,
    val phoneNumber: String,
    val gender: Gender,
    val birth: String
) {

    companion object {
        fun fromEntity(memberEntity: MemberEntity): MemberCreateResponse {
            return with(memberEntity) {
                MemberCreateResponse(
                    memberId = id,
                    loginId = loginId,
                    name = name,
                    phoneNumber = phoneNumber,
                    gender = gender,
                    birth = birth
                )
            }
        }
    }
}