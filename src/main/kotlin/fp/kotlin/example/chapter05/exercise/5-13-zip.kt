package fp.kotlin.example.chapter05.exercise

import fp.kotlin.example.chapter05.*
import kotlin.system.measureTimeMillis

/**
 *
 * 연습문제 5-13
 *
 * zip 함수는 3장에서 이미 설명했다. 여기서는 직접 FunList에 zip 함수를 작성해보자.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다.
 *
 */

fun main() {
    measureTimeMillis {
//        (1..10000).toFunList().zip((1..10000).toFunList())
        val intList = funListOf(1, 2, 3, 4, 5)
        val charList = funListOf('a', 'b', 'c', 'd', 'e')
        require(intList.zip(charList) == funListOf(1 to 'a', 2 to 'b', 3 to 'c', 4 to 'd', 5 to 'e'))
        require(intList.zip(funListOf('a', 'b', 'c')) == funListOf(1 to 'a', 2 to 'b', 3 to 'c'))
        require(charList.zip(funListOf(1, 2, 3)) == funListOf('a' to 1, 'b' to 2, 'c' to 3))
    }.also { println(it) }
}

// appendTail() 1만개
// 1017, 922, 946 (ms)

// addHead(), reverse() 1만개
// 38, 50, 44 (ms)

tailrec fun <T, R> FunList<T>.zip(
    other: FunList<R>,
    acc: FunList<Pair<T, R>> = FunList.Nil
): FunList<Pair<T, R>> = when {
    this is FunList.Nil || other is FunList.Nil ->
        acc.reverse()
    else -> {
        val newAcc = acc.addHead(getHead() to other.getHead())
        getTail().zip(other.getTail(), newAcc)
    }
}
