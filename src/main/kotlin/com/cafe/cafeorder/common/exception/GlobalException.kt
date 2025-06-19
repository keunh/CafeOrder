package com.cafe.cafeorder.common.exception

import java.lang.RuntimeException

class GlobalException(
    val errorCode: ErrorCode,
    throwable: Throwable?,
    override val message: String = errorCode.message
): RuntimeException(throwable) {

    constructor(errorCode: ErrorCode) : this(errorCode, null)
}