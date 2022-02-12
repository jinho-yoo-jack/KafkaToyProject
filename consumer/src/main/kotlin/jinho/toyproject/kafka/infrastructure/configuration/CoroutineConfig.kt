package jinho.toyproject.kafka.infrastructure.configuration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.reactor.asCoroutineDispatcher
import reactor.core.scheduler.Schedulers


/**
 * [Schedulers.boundedElastic] 을 이용하여 코루틴에서 블록킹 로직을 처리하기 위한 디스패처
 */
val boundedElasticDispatcher = Schedulers.boundedElastic()
    .asCoroutineDispatcher()


/**
 * [boundedElasticDispatcher] 를 컨택스트로 하는 코루틴 스코프
 *
 * > Every coroutine builder and every scoping function provides its own scope
 * with its own Job instance into the inner block of code it runs.
 *
 * @see CoroutineScope
 *
 */
val boundedElasticScope: CoroutineScope
    get() = CoroutineScope(boundedElasticDispatcher)