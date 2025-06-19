package com.cafe.cafeorder.config.exception

import ch.qos.logback.classic.Level
import com.cafe.cafeorder.common.exception.GlobalException
import com.cafe.cafeorder.common.exception.ErrorResponse
import com.cafe.cafeorder.common.utils.Log
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class ExceptionHandler {

    companion object : Log {
        const val DEFAULT_EXCEPTION_MESSAGE = "서버 오류입니다. 다시 시도해주세요."
    }

    @ExceptionHandler(Exception::class)
    fun unExpectedExceptionHandler(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    displayMessage = DEFAULT_EXCEPTION_MESSAGE,
                    errorCode = 0,
                )
            )
    }

    @ExceptionHandler(GlobalException::class)
    fun unExpectedExceptionHandler(e: GlobalException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        when (e.errorCode.logLevel) {
            Level.ERROR -> log.error(e.message)
            else -> {}
        }

        return ResponseEntity
            .status(e.errorCode.status)
            .body(
                ErrorResponse(
                    displayMessage = e.errorCode.displayMessage,
                    errorCode = 0,
                )
            )
    }
}