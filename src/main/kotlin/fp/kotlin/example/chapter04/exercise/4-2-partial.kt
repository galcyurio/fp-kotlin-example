package fp.kotlin.example.chapter04.exercise

/**
 * 연습문제 4-2
 *
 * 매개변수 3개를 받는 부분 적용 함수 3개를 직접 구현하라.
 */

private fun <A, B, C, D> ((A, B, C) -> D).partial1(a: A): (B, C) -> D =
    { b, c -> this(a, b, c) }

private fun <A, B, C, D> ((A, B, C) -> D).partial2(b: B): (A, C) -> D =
    { a, c -> this(a, b, c) }

private fun <A, B, C, D> ((A, B, C) -> D).partial3(c: C): (A, B) -> D =
    { a, b -> this(a, b, c) }

fun main() {
    /*
     * 주석을 해제하고 partial1, partial2, partial3을 구현해보세요.
     */

    val func = { a: Int, b: Int, c: Int -> a + b + c }

    val partiallyAppliedFunc1 = func.partial1(1)
    require(6 == partiallyAppliedFunc1(2, 3))

    val partiallyAppliedFunc2 = func.partial2(2)
    require(6 == partiallyAppliedFunc2(1, 3))

    val partiallyAppliedFunc3 = func.partial3(3)
    require(6 == partiallyAppliedFunc3(1, 2))
}