package com.cafe.cafeorder.common.exception

class ErrorResponse(
    val displayMessage: String,
    val errorCode: Int = 0,
)