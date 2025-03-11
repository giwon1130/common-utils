package com.giwon1130.common.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.defaultMessage)
