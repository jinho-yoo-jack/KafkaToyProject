package jinho.toyproject.kafka.infrastructure.kafka.listener

import jinho.toyproject.kafka.common.extension.isNotNullOrEmptyThen
import jinho.toyproject.kafka.infrastructure.configuration.boundedElasticScope
import jinho.toyproject.kafka.infrastructure.kafka.KafkaConsumerConfig
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionStatus
import reactor.kafka.receiver.ReceiverRecord
import java.time.Duration

abstract class AbstractKafkaGroupListener(
    private val kafkaConsumerConfig: KafkaConsumerConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val BUFFERING_TIME = 40L
    }

    fun subscribe(topicName: String, groupId: String, partitionNo: Int) {
        kafkaConsumerConfig.kafkaReceiver(topicName, groupId, partitionNo)
            .receive()
            .groupBy {
                it.value()
            }
            .flatMap {
                it.buffer(Duration.ofSeconds(BUFFERING_TIME))
            }
            .subscribe {
                boundedElasticScope.launch { process(topicName, it) }
            }

    }

    private suspend fun process(topicName: String, recordList: List<ReceiverRecord<String, String>>) {
        recordList.mapNotNull { record ->
            val offset = record.receiverOffset()
            try {
                record.value().let { value ->
                    val connectionInfo = value
                        ?: throw RuntimeException("record is NULL")
                } ?: throw RuntimeException("record is NULL")

            } catch (e: Exception) {
                log.error("process failed", e)

            } finally {
                offset.acknowledge()
            }
        }.isNotNullOrEmptyThen { records ->
            //TODO: RDB에 저장하는 로직 적용필요
            runInTransaction(topicName, records) { jdbcTemplate ->
                this.consumeMessage(jdbcTemplate, 0, records.toList())
            }
        }
    }

    private suspend fun <R> runInTransaction(
        topicName: String,
        records: Collection<Any?>,
        block: suspend (jdbcTemplate: JdbcTemplate) -> R
    ) {
        val transactionManager = DataSourceTransactionManager()
        val status: TransactionStatus? = null

        try {
            val dataSource = records
                .first()

        } catch (e: Exception) {
            log.error("Error - Transaction", e.stackTrace)
        }
    }

    protected abstract suspend fun consumeMessage(
        jdbcTemplate: JdbcTemplate,
        godoSno: Long,
        records: List<Any?>,
    )

}