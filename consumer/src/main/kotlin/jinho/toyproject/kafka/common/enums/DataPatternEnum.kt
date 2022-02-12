package jinho.toyproject.kafka.common.enums

import java.time.format.DateTimeFormatter

enum class DatePatternEnum(val value: String) {
    DATE_DEFAULT("yyyy-MM-dd"),
    DATETIME_DEFAULT("yyyy-MM-dd HH:mm:ss");

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(value)
}