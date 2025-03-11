package com.example.common.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * 현재 시간을 포맷된 문자열로 반환 (기본: 시스템 시간대)
     */
    fun nowFormatted(): String {
        return LocalDateTime.now().format(formatter)
    }

    /**
     * 특정 시간대의 현재 시간을 포맷된 문자열로 반환
     */
    fun nowFormatted(zoneId: ZoneId): String {
        return LocalDateTime.now(zoneId).format(formatter)
    }

    /**
     * 주어진 날짜를 포맷된 문자열로 변환
     */
    fun formatDate(date: Date, zoneId: ZoneId = ZoneId.systemDefault()): String {
        return date.toInstant()
            .atZone(zoneId)
            .toLocalDateTime()
            .format(formatter)
    }
}
