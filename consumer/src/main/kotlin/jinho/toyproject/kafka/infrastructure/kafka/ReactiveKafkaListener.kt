package jinho.toyproject.kafka.infrastructure.kafka

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class ReactiveKafkaListener(
    val topic: Topic,
    val groupId: String = "1",
    val partitionSize: Int = 1
)
