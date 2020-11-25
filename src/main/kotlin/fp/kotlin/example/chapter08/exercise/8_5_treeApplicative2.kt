package fp.kotlin.example.chapter08.exercise

import fp.kotlin.example.chapter04.solution.curried
import fp.kotlin.example.chapter08.Node
import fp.kotlin.example.chapter08.Tree
import fp.kotlin.example.chapter08.apply
import fp.kotlin.example.chapter08.pure

/**
 *
 * 연습문제 8-5
 *
 * 아래 두 트리를 apply로 결합한 트리를 프로그램으로 만들어보고, 결과가 맞는지 확인해보자.
 * Node(1, listOf(Node(2, listOf(Node(3))), Node(4, listOf())))
 * Node(5, listOf(Node(6), Node(7, listOf(Node(8), Node(9)))))
 */

fun main() {

    val tree: Tree<Int> = Node(1 * 5, listOf(
        Node(1 * 6),
        Node(1 * 7, listOf(
            Node(1 * 8),
            Node(1 * 9)
        )),
        Node(2 * 5, listOf(
            Node(2 * 6),
            Node(2 * 7, listOf(
                Node(2 * 8),
                Node(2 * 9)
            )),
            Node(3 * 5, listOf(
                Node(3 * 6),
                Node(3 * 7, listOf(
                    Node(3 * 8),
                    Node(3 * 9)
                ))
            ))
        )),
        Node(4 * 5, listOf(
            Node(4 * 6),
            Node(4 * 7, listOf(
                Node(4 * 8),
                Node(4 * 9)
            ))
        ))
    ))

    require(tree == Tree.pure({ x: Int, y: Int -> x * y }.curried())
        apply Node(1, listOf(Node(2, listOf(Node(3))), Node(4, listOf())))
        apply Node(5, listOf(Node(6), Node(7, listOf(Node(8), Node(9)))))
    )
}