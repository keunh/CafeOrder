package com.cafe.cafeorder.fixtures.member.repository

import com.cafe.cafeorder.repository.member.MemberRepository
import com.cafe.cafeorder.repository.member.entity.MemberEntity
import com.cafe.cafeorder.repository.order.entity.OrderEntity
import java.util.concurrent.ConcurrentHashMap

class FakeMemberRepository: MemberRepository {

    companion object {
        var ID = 1L
        val ENTITY_MANAGER = ConcurrentHashMap<Long, MemberEntity>()
    }

    override fun existsByLoginId(loginId: String): Boolean {
        return ENTITY_MANAGER.values.any { it.loginId == loginId }
    }

    override fun findById(memberId: Long): MemberEntity? {
        return ENTITY_MANAGER[memberId]
    }

    override fun <T> save(entity: T): T {
        val memberEntity = entity as MemberEntity
        val copyEntity = with(memberEntity) {
            MemberEntity(
                id = ID,
                loginId = loginId,
                name = name,
                phoneNumber = phoneNumber,
                gender = gender,
                birth = birth,
                status = status,
                withdrawnAt = withdrawnAt,
                restoreAt = restoreAt
            )
        }
        ENTITY_MANAGER[ID++] = copyEntity
        return copyEntity as T
    }

    override fun deleteAll() {
        ENTITY_MANAGER.clear()
    }
}