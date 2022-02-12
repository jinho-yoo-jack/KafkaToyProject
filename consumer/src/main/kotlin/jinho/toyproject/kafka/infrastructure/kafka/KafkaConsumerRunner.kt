package jinho.toyproject.kafka.infrastructure.kafka

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.getBeansWithAnnotation
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import kotlin.reflect.full.findAnnotation

@Component
class KafkaConsumerRunner(private val ctx: ApplicationContext) : ApplicationRunner {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        /*
         Spring Boot가 생성한 Bean's(in Ioc==ApplicationContext) 중에 @ReactiveKafkaListener 어노테이션이 할당되어 있는
         모든 Beans을 가져온다.
        */
        ctx.getBeansWithAnnotation(ReactiveKafkaListener::class.java).forEach { (_, listener) ->
            val annotation = listener.javaClass.kotlin.findAnnotation<ReactiveKafkaListener>()!!
            val topicName: String = annotation.topic.value
            val groupId: String = annotation.groupId
            val partitionSize: Int = annotation.partitionSize

            try {
                for (index in 0 until partitionSize) {
                    listener.javaClass.getMethod("subscribe", String::class.java, String::class.java, Int::class.java)
                        .invoke(listener, topicName, groupId, partitionSize)
                }
            } catch (e: Exception) {
                log.error("$topicName Subscribe Failed", e)
            }
        }
    }

}