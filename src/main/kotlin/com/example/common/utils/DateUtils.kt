package com.example.common.utils

object ResponseUtils {
    fun success(data: Any?): Map<String, Any?> {
        return mapOf(
            "status" to "SUCCESS",
            "message" to "요청이 정상 처리되었습니다.",
            "data" to data
        )
    }

    fun failure(message: String, errorCode: String): Map<String, Any> {
        return mapOf(
            "status" to "FAIL",
            "message" to message,
            "errorCode" to errorCode
        )
    }
}
