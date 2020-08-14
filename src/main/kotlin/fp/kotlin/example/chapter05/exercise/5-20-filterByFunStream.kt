package fp.kotlin.example.chapter05.exercise

import fp.kotlin.example.chapter05.*

/**
 *
 * 연습문제 5-20
 *
 * FunList에서 작성했던 filter 함수를 FunStream에도 추가하자.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다.
 *
 */

fun main() {
    require(funStreamOf(1, 2, 3, 4, 5)
        .filter { it % 2 == 0 } == funStreamOf(2, 4))
    require(funStreamOf(1, 2, 3, 4, 5)
        .filter { it > 6 } == FunStream.Nil)
    require((1..100000000)
        .toFunStream()
        .filter { it > 100 }
        .getHead() == 101)
}

// foldLeft() 내부에서 값이 평가됨
//fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = foldLeft(FunStream.Nil) { acc: FunStream<T>, x: T ->
//    if (p(x)) {
//        acc.appendTail(x)
//    } else {
//        acc
//    }
//}

fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> when (val dropped = dropWhile(p)) {
        FunStream.Nil -> FunStream.Nil
        is FunStream.Cons -> FunStream.Cons(
            head = { dropped.head() },
            tail = { dropped.tail().filter(p) }
        )
    }
}