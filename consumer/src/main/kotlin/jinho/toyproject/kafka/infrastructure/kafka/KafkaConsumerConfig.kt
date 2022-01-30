package jinho.toyproject.kafka.infrastructure.kafka

import org.apache.kafka.common.TopicPartition
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.stereotype.Component
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class KafkaConsumerConfig(val kafkaProperties: KafkaProperties) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun kafkaReceiver(topicName: String, groupId: String, partitionNo: Int): KafkaReceiver<String, String> {
        val options =
            ReceiverOptions.create<String, String>(kafkaProperties.buildConsumerProperties())
                .assignment(setOf(TopicPartition(topicName, partitionNo)))
                .addAssignListener { log.debug("onPartitionAssigned $it")}
                .addRevokeListener { log.debug("onPartitionRevoked $it")}

        return KafkaReceiver.create(options)
    }
}