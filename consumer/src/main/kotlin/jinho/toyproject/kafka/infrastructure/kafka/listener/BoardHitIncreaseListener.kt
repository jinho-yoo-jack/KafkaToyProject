package jinho.toyproject.kafka.infrastructure.kafka.listener

import jinho.toyproject.kafka.common.extension.isNotNullOrEmptyThen
import jinho.toyproject.kafka.infrastructure.kafka.KafkaConsumerConfig
import jinho.toyproject.kafka.infrastructure.kafka.ReactiveKafkaListener
import jinho.toyproject.kafka.infrastructure.kafka.Topic
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate

@ReactiveKafkaListener(topic= Topic.KAFKA_STUDY_TOPIC, partitionSize = 1)
class BoardHitIncreaseListener(
    kafkaConsumerConfig: KafkaConsumerConfig,
): AbstractKafkaGroupListener(kafkaConsumerConfig,) {

    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun consumeMessage(jdbcTemplate: JdbcTemplate, godoSno: Long, records: List<Any?>) {
        records
            .mapNotNull { }
            .isNotNullOrEmptyThen { }

    }
}