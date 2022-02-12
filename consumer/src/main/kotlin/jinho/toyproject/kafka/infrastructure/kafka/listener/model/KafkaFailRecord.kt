package jinho.toyproject.kafka.infrastructure.kafka.listener.model

import reactor.kafka.receiver.ReceiverRecord
import java.time.LocalDateTime

data class KafkaFailRecord(
    val topicName: String,
    val key: Any? = null,
    val value: Any? = null,
    val failMessage: String,
    val createDate: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun createBy(record: ReceiverRecord<String, String>, failMessage: String) = KafkaFailRecord(
            topicName = record.topic(),
            key = record.key(),
            value = record.value(),
            failMessage = failMessage,
            createDate = LocalDateTime.now()
        )
    }
}