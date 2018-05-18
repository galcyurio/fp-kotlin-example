package fp.kotlin.example.chapter05.solution

import fp.kotlin.example.chapter05.FunStream
import fp.kotlin.example.chapter05.funStreamOf

/**
 *
 * 연습문제 5-18
 *
 * FunList에서 작성했던 sum 함수를 FunStream에도 추가하자.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다.
 *
 */

fun main(args: Array<String>) {
    require(funStreamOf(1, 2, 3, 4, 5).sum() == 1 + 2 + 3 + 4 + 5)
}

fun FunStream<Int>.sum(): Int = when (this) {
    FunStream.Nil -> 0
    is FunStream.Cons -> head() + tail().sum()
}