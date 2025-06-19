package com.cafe.cafeorder.service.member

import com.cafe.cafeorder.common.exception.ErrorCode
import com.cafe.cafeorder.controller.member.dto.MemberCreateRequest
import com.cafe.cafeorder.fixtures.member.repository.FakeMemberRepository
import com.cafe.cafeorder.repository.member.constants.Gender
import com.cafe.cafeorder.repository.member.constants.MemberStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime


class MemberServiceTest {

    private val memberRepository = FakeMemberRepository()
    private val memberService = MemberService(memberRepository)

    @AfterEach
    fun tearDown() {
        memberRepository.deleteAll()
    }

    @Test
    fun `멤버 생성에 성공한다`() {
        // given
        val request = createMemberCreateRequest()

        // when
        val member = memberService.createMember(request)

        // then
        assertThat(member.loginId).isEqualTo(request.loginId)
        assertThat(member.birth).isEqualTo(request.birth)
        assertThat(member.name).isEqualTo(request.name)
        assertThat(member.gender).isEqualTo(request.gender)
        assertThat(member.phoneNumber).isEqualTo(request.phoneNumber)
    }

    @Test
    fun `멤버 생성에 실패한다 - 아이디 중복`() {
        // given
        val request = createMemberCreateRequest()
        memberService.createMember(request)

        // when, then
        assertThatThrownBy {
            memberService.createMember(request)
        }.hasMessageContaining(ErrorCode.ALREADY_REGISTERED_MEMBER.message)
    }

    @Test
    fun `멤버 조회에 성공한다`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)

        // when
        val findMember = memberService.findMember(member.memberId)

        // then
        assertThat(findMember.memberId).isEqualTo(member.memberId)
    }

    @Test
    fun `멤버 조회 시 탈퇴한 회원은 조회 실패한다`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)
        memberService.withdrawMember(member.memberId)

        // when, then
        assertThatThrownBy {
            memberService.findMember(member.memberId)
        }.hasMessageContaining(ErrorCode.CANNOT_FIND_MEMBER.message)
    }

    @Test
    fun `탈퇴에 성공한다`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)

        // when
        memberService.withdrawMember(member.memberId)

        // then
        val findMember = memberRepository.findById(member.memberId)!!
        assertThat(findMember.status).isEqualTo(MemberStatus.WITHDRAWN)
        assertThat(findMember.withdrawnAt).isNotNull()
    }

    @Test
    fun `탈퇴에 실패한다 - 이미 탈퇴`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)
        memberService.withdrawMember(member.memberId)

        // when, then
        assertThatThrownBy {
            memberService.withdrawMember(member.memberId)
        }.hasMessageContaining(ErrorCode.WITHDRAW_MEMBER.message)
    }

    @Test
    fun `계정 복구에 성공한다`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)
        memberService.withdrawMember(member.memberId)

        // when
        memberService.restoreMember(member.memberId)

        // then
        val findMember = memberRepository.findById(member.memberId)!!
        assertThat(findMember.status).isEqualTo(MemberStatus.AVAILABLE)
        assertThat(findMember.withdrawnAt).isNull()
        assertThat(findMember.restoreAt).isNotNull()
    }

    @Test
    fun `계정 복구에 실패한다 - 탈퇴한 회원이 아님`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)

        // when, then
        assertThatThrownBy {
            memberService.restoreMember(member.memberId)
        }.hasMessageContaining(ErrorCode.CANNOT_RESTORE_MEMBER.message)
    }

    @Test
    fun `계정 복구에 실패한다 - 탈퇴 후 30일이 지남`() {
        // given
        val request = createMemberCreateRequest()
        val member = memberService.createMember(request)
        memberService.withdrawMember(member.memberId)

        // when, then
        assertThatThrownBy {
            memberService.restoreMember(member.memberId, LocalDateTime.now().plusDays(40))
        }.hasMessageContaining(ErrorCode.CANNOT_RESTORE_MEMBER_BECAUSE_OF_DAYS.message)
    }

    fun createMemberCreateRequest(): MemberCreateRequest {
        return MemberCreateRequest(
            loginId = "TEST_LOGIN_ID",
            name = "TEST_NAME",
            phoneNumber = "01011112222",
            gender = Gender.M,
            birth = "901010"
        )
    }
}