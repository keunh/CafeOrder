package com.cafe.cafeorder.common.exception

import ch.qos.logback.classic.Level
import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
    val displayMessage: String,
    val logLevel: Level = Level.WARN,
    val code: Int = 0,
) {

    // 회원
    ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "이미 등록된 회원입니다.", "이미 등록된 회원입니다."),
    CANNOT_FIND_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.", "존재하지 않는 회원입니다."),
    WITHDRAW_MEMBER(HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다.", "탈퇴한 회원입니다."),
    CANNOT_RESTORE_MEMBER(HttpStatus.BAD_REQUEST, "미탈퇴회원으로 복구가 불가능합니다.", "미탈퇴회원으로 복구가 불가능합니다."),
    CANNOT_RESTORE_MEMBER_BECAUSE_OF_DAYS(HttpStatus.BAD_REQUEST, "탈퇴 후 30일이 지났습니다.", "탈퇴 후 30일이 지났습니다."),

    // 상품
    CANNOT_FIND_ITEM(HttpStatus.BAD_REQUEST, "존재하지 않는 상품입니다.", "존재하지 않는 상품입니다."),
    ALREADY_SAVED_ITEM(HttpStatus.BAD_REQUEST, "이미 등록된 상품입니다.", "이미 등록된 상품입니다."),

    // 주문
    CANNOT_FIND_ORDER(HttpStatus.BAD_REQUEST, "존재하지 않는 주문입니다.", "존재하지 않는 상품입니다."),
    FAIL_ORDER(HttpStatus.BAD_REQUEST, "주문이 실패하였습니다. 다시 시도해주세요.", "주문이 실패하였습니다. 다시 시도해주세요."),
    CANNOT_CANCEL_ORDER_STATUS(HttpStatus.BAD_REQUEST, "취소할 수 없는 주문입니다.", "취소할 수 없는 주문입니다."),
    FAIL_CANCEL_ORDER(HttpStatus.BAD_REQUEST, "주문 취소가 실패하였습니다. 다시 시도해주세요.", "주문 취소가 실패하였습니다. 다시 시도해주세요."),
}