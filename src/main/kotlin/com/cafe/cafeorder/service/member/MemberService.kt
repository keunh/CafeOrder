package com.cafe.cafeorder.service.member

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.common.exception.GlobalException
import com.cafe.cafeorder.controller.member.dto.MemberCreateRequest
import com.cafe.cafeorder.controller.member.dto.MemberCreateResponse
import com.cafe.cafeorder.controller.member.dto.MemberResponse
import com.cafe.cafeorder.repository.member.entity.MemberEntity
import com.cafe.cafeorder.repository.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun createMember(request: MemberCreateRequest): MemberCreateResponse {
        val isExists = memberRepository.existsByLoginId(request.loginId)
        if (isExists) {
            throw GlobalException(ErrorCode.ALREADY_REGISTERED_MEMBER)
        }

        val member = memberRepository.save(request.toEntity())
        return MemberCreateResponse.fromEntity(member)
    }

    @Transactional(readOnly = true)
    fun findMember(memberId: Long): MemberResponse {
        val member = findByMemberId(memberId)
        if (member.isWithdrawn()) {
            throw GlobalException(ErrorCode.CANNOT_FIND_MEMBER)
        }
        return MemberResponse.fromEntity(member)
    }

    @Transactional
    fun withdrawMember(memberId: Long) {
        val member = findByMemberId(memberId)
        if (member.isWithdrawn()) {
            throw GlobalException(ErrorCode.WITHDRAW_MEMBER)
        }
        val now = LocalDateTime.now()
        member.withdraw(now)
    }

    @Transactional
    fun restoreMember(memberId: Long, now: LocalDateTime = LocalDateTime.now()) {
        val member = findByMemberId(memberId)
        if (!member.isWithdrawn()) {
            throw GlobalException(ErrorCode.CANNOT_RESTORE_MEMBER)
        }

        if (!member.isWithdrawalIn30Days(now)) {
            throw GlobalException(ErrorCode.CANNOT_RESTORE_MEMBER_BECAUSE_OF_DAYS)
        }

        member.restore(now)
    }

    @Transactional(readOnly = true)
    fun validateMember(memberId: Long) {
        findByMemberId(memberId)
    }

    private fun findByMemberId(memberId: Long): MemberEntity {
        return memberRepository.findById(memberId)
            ?: throw GlobalException(ErrorCode.CANNOT_FIND_MEMBER)
    }
}