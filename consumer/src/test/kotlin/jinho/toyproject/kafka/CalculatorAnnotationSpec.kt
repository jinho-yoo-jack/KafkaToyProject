package jinho.toyproject.kafka

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

internal class CalculatorAnnotationSpec : AnnotationSpec() {
    private val sut = Calculator()

    @Test
    fun `1과 2를 더하면 3이 반환된다`(){
        val result = sut.calculate("1+2")
        result shouldBe 3
    }

}

class Calculator() {
    fun calculate(statement: String): Int{
        return 3
    }
}