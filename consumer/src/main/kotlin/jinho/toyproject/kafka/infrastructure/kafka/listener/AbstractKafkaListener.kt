package jinho.toyproject.kafka.infrastructure.kafka.listener

import jinho.toyproject.kafka.common.extension.toPrettyJson
import jinho.toyproject.kafka.infrastructure.configuration.boundedElasticScope
import jinho.toyproject.kafka.infrastructure.kafka.KafkaConsumerConfig
import jinho.toyproject.kafka.infrastructure.kafka.listener.model.KafkaFailRecord
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import reactor.kafka.receiver.ReceiverRecord

abstract class AbstractKafkaListener(
    private val kafkaConsumerConfig: KafkaConsumerConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun subscribe(topicName: String, groupId: String, partitionNo: Int) {
        kafkaConsumerConfig.kafkaReceiver(topicName, groupId, partitionNo)
            .receive()
            .subscribe {
                boundedElasticScope.launch { process(it) }
            }
    }

    private suspend fun process(record: ReceiverRecord<String, String>) {
        try {
            val offset = record.receiverOffset();
            this.consumeMessage(record.value())
            offset.acknowledge()
        } catch (e: Exception) {
            handleError(e, record)
        }
    }

    private fun printErrorLog(e: Exception, failRecord: KafkaFailRecord) {
        log.error(
            """
                error : ${e.message}
                ${failRecord.toPrettyJson()}
            """.trimIndent(), e
        )
    }

    protected abstract suspend fun consumeMessage(record: String)
    private fun handleError(e: Exception, record: ReceiverRecord<String, String>) {
        log.error("[Kafka Consume Error] - TOPIC: [${record.topic()}], DATA: ${record.value()}", e)
    }
}