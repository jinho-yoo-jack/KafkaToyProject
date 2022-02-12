package jinho.toyproject.kafka.common.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import jinho.toyproject.kafka.common.enums.DatePatternEnum
import java.time.LocalDateTime


val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerModule(KotlinModule(nullIsSameAsDefault = true, nullToEmptyCollection = true, nullToEmptyMap = true))
    registerModule(Hibernate5Module())
    registerModule(JavaTimeModule().apply {
        addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DatePatternEnum.DATETIME_DEFAULT.formatter)
        )
        addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(DatePatternEnum.DATETIME_DEFAULT.formatter)
        )
    })
    enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}

fun Any.toPrettyJson(): String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)