package com.giwon1130.common.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun nowFormatted(): String {
        return LocalDateTime.now().format(formatter)
    }
}
