package com.giwon1130.common.response

data class CommonResponse<T>(
    val status: String = "SUCCESS",
    val message: String = "요청이 정상 처리되었습니다.",
    val data: T?
) {
    companion object {
        fun <T> success(message: String = "요청이 정상 처리되었습니다.", data: T? = null): CommonResponse<T> {
            return CommonResponse(status = "SUCCESS", message = message, data = data)
        }
    }
}
