package com.example.common.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.defaultMessage)
