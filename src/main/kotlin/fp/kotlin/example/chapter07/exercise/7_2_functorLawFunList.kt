package fp.kotlin.example.chapter07.exercise

import fp.kotlin.example.chapter04.compose

/**
 *
 * 연습문제 7-2
 *
 * 연습문제에서 만들어본 리스트 펑터인 FunList가 펑터의 법칙을 만족하는지 확인해보자.
 */

fun main() {
    val funList: FunList<Int> = Cons(1, Cons(2, Cons(3, Nil)))

    // functor 1lows
    require(funList.fmap(::identity) == identity(funList))

    // functor 2lows
    val f = { x: Int -> x + 2 }
    val g = { x: Int -> x * 3 }
    val left = funList.fmap(f compose g)
    val right = funList.fmap(g).fmap(f)

    require(left == right)
}

fun <T> identity(value: T): T = value