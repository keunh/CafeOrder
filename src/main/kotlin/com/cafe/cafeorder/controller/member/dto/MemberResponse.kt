package com.cafe.cafeorder.controller.member.dto

import com.cafe.cafeorder.repository.member.entity.MemberEntity

class MemberResponse(
    val memberId: Long,
    val loginId: String,
    val name: String,
) {
    companion object {
        fun fromEntity(memberEntity: MemberEntity): MemberResponse {
            return with(memberEntity) {
                MemberResponse(
                    memberId = id,
                    loginId = loginId,
                    name = name
                )
            }
        }
    }
}