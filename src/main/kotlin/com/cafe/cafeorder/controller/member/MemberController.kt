package com.cafe.cafeorder.controller.member

import com.cafe.cafeorder.controller.member.dto.MemberCreateRequest
import com.cafe.cafeorder.controller.member.dto.MemberCreateResponse
import com.cafe.cafeorder.controller.member.dto.MemberResponse
import com.cafe.cafeorder.service.member.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/api/v1/members/member")
    fun createMember(@RequestBody request: MemberCreateRequest): ResponseEntity<MemberCreateResponse> {
        val memberCreateResponse = memberService.createMember(request)
        return ResponseEntity.ok(memberCreateResponse)
    }

    @GetMapping("/api/v1/members/member/{memberId}")
    fun findMember(@PathVariable memberId: Long): ResponseEntity<MemberResponse> {
        val memberResponse = memberService.findMember(memberId)
        return ResponseEntity.ok(memberResponse)
    }

    @DeleteMapping("/api/v1/members/member/{memberId}")
    fun withdrawalMember(@PathVariable memberId: Long) {
        memberService.withdrawMember(memberId)
    }

    @PutMapping("/api/v1/members/member/restore/{memberId}")
    fun restoreMember(@PathVariable memberId: Long) {
        memberService.restoreMember(memberId)
    }
}