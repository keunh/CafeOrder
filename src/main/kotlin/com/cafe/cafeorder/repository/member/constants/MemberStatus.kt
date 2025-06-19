package com.cafe.cafeorder.repository.member.constants

enum class MemberStatus (
    val description: String
) {
    AVAILABLE("이용 가능"),
    WITHDRAWN("탈퇴"),
    SUSPEND("정지")
}