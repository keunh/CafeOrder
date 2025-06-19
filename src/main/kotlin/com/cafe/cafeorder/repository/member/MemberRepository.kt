package com.cafe.cafeorder.repository.member

import com.cafe.cafeorder.repository.member.entity.MemberEntity
import com.example.kakaopayad.repository.common.BaseRepository

interface MemberRepository: BaseRepository<MemberEntity, Long> {

    fun existsByLoginId(loginId: String): Boolean

    fun findById(memberId: Long): MemberEntity?

    fun deleteAll()
}