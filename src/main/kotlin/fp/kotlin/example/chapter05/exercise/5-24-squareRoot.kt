package fp.kotlin.example.chapter05.exercise

import kotlin.math.sqrt

/**
 *
 * 연습문제 5-24
 *
 * 모든 자연수의 제곱근의 합이 1000을 넘으려면 몇개의 자연수가 필요한지 계산하는 함수를 작성해보자.
 *
 * 힌트: 함수는 꼬리재귀로 작성하자.
 *
 */

fun main() {
    require(square() == 14)
}

tailrec fun square(acc: Int = 0, n: Int = 1): Int = when {
    acc > 1000 -> n - 1
    else -> square(n * n + acc, n + 1)
}

// 문제가 잘못된 것 같다. 제곱근이 아닌 제곱의 합을 계산해야 한다.
tailrec fun squareRoot(acc: Double = 0.0, n: Int = 1): Int = when {
    acc > 1000 -> n - 1
    else -> squareRoot(acc + sqrt(n.toDouble()), n + 1)
}