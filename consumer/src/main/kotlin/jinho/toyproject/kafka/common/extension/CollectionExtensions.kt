package jinho.toyproject.kafka.common.extension


inline fun <T> Collection<T>?.isNullOrEmptyThen(block: () -> Unit) {
    if (this?.isEmpty() != false) block()
}

/* 확장함수
*  - this == Collection<T>
*/
inline fun <T, R> Collection<T>?.isNotNullOrEmptyThen(block: (Collection<T>) -> R): R? {
    return this?.run {
        if (this.isNotEmpty()) block(this)
        else null
    }
}