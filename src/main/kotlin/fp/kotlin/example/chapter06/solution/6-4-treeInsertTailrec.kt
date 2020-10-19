package fp.kotlin.example.chapter06.solution

import fp.kotlin.example.chapter05.FunStream
import fp.kotlin.example.chapter05.addHead
import fp.kotlin.example.chapter05.funStreamOf

/**
 *
 * 연습문제 6-4
 *
 * SOF가 일어나지 않도록 insertTailrec을 작성해보자.
 *
 * 힌트 : 함수의 선언 타입은 아래와 같다.
 *       필요하다면 내부 함수를 별도로 생성하자.
 *
 */

fun main() {
    val tree1 = EmptyTree.insertTailrec(5)
    require(tree1 == Node(5, EmptyTree, EmptyTree))

    val tree2 = tree1.insertTailrec(3)
    require(tree2 ==
        Node(5,
            Node(3, EmptyTree, EmptyTree),
            EmptyTree)
    )

    val tree3 = tree2.insertTailrec(10)
    require(tree3 ==
        Node(5,
            Node(3, EmptyTree, EmptyTree),
            Node(10, EmptyTree, EmptyTree)
        )
    )

    val tree4 = tree3.insertTailrec(20)
    require(tree4 ==
        Node(5,
            Node(3, EmptyTree, EmptyTree),
            Node(10,
                EmptyTree,
                Node(20, EmptyTree, EmptyTree)
            )
        )
    )

    val tree5 = tree4.insertTailrec(4)
    require(tree5 ==
        Node(5,
            Node(3,
                EmptyTree,
                Node(4, EmptyTree, EmptyTree)),
            Node(10,
                EmptyTree,
                Node(20, EmptyTree, EmptyTree)
            )
        )
    )

    val tree6 = tree5.insertTailrec(2)
    require(tree6 ==
        Node(5,
            Node(3,
                Node(2, EmptyTree, EmptyTree),
                Node(4, EmptyTree, EmptyTree)
            ),
            Node(10,
                EmptyTree,
                Node(20, EmptyTree, EmptyTree)
            )
        )
    )

    val tree7 = tree6.insertTailrec(8)
    require(tree7 ==
        Node(5,
            Node(3,
                Node(2, EmptyTree, EmptyTree),
                Node(4, EmptyTree, EmptyTree)
            ),
            Node(10,
                Node(8, EmptyTree, EmptyTree),
                Node(20, EmptyTree, EmptyTree)
            )
        )
    )

    (1..100000).fold(EmptyTree as Tree<Int>) { acc, i ->
        acc.insertTailrec(i)
    }

}

fun Tree<Int>.insertTailrec(elem: Int): Tree<Int> = rebuild(path(this, elem), elem)

// 값을 추가할 Node 위치를 찾는 함수 
private fun path(tree: Tree<Int>, value: Int): FunStream<Pair<Tree<Int>, Boolean>> {

    tailrec fun loop(
        tree: Tree<Int>,
        path: FunStream<Pair<Tree<Int>, Boolean>>
    ): FunStream<Pair<Tree<Int>, Boolean>> = when (tree) {
        is Node -> when {
            // Tree에 추가하려는 값과 현재 Node의 값 비교

            // 작으면 왼쪽 Node 탐색하고 현재 Node는 false와 함께 path에 추가
            value < tree.value -> loop(tree.leftTree, path.addHead(tree to false))

            // 크거나 같으면 오른쪽 Node 탐색하고 현재 Node는 true와 함께 path에 추가
            else -> loop(tree.rightTree, path.addHead(tree to true))

            // false는 왼쪽(작음), true는 오른쪽(크거나 같음)
        }

        // EmptyTree가 발견되면 path에 최종적으로 추가된 Node에
        // true면 오른쪽, false면 왼쪽에 값을 추가하면 되므로 path를 반환
        EmptyTree -> path
    }

    return loop(tree, funStreamOf())
}

// path 함수를 통해서 어느 위치에 추가하면 되는지는 알았지만
// 함수형 프로그래밍에서는 불변이 원칙이기 때문에 `val`로 선언되어 있다.
// 따라서 추가되어야하는 Node를 참조하는 모든 Node들(path)을 새로 생성해주어야 한다.
private fun rebuild(path: FunStream<Pair<Tree<Int>, Boolean>>, value: Int): Tree<Int> {

    tailrec fun loop(path: FunStream<Pair<Tree<Int>, Boolean>>, subTree: Tree<Int>): Tree<Int> =
        when (path) {
            // Node와 함께 넘어온 Boolean 값을 확인
            is FunStream.Cons -> when ((path.head()).second) {
                false -> {// value가 현재 Node보다 작은 경우
                    // 여기서 생성되는 Node는 `val`로 선언되어 있는 `leftTree`를 바꾸어주는 대신 복사하는 것
                    val newSubTree = Node(
                        value = (path.head().first as Node).value,
                        leftTree = subTree,// 더 작으므로 새로 추가된 Node를 왼쪽에 위치시킨다.
                        rightTree = (path.head().first as Node).rightTree
                    )
                    loop(path.tail(), newSubTree)
                }
                true -> {// value가 현재 Node보다 크거나 같은 경우
                    // 여기서 생성되는 Node는 `val`로 선언되어 있는 `rightTree`를 바꾸어주는 대신 복사하는 것
                    val newSubTree = Node(
                        value = (path.head().first as Node).value,
                        leftTree = (path.head().first as Node).leftTree,
                        rightTree = subTree// 더 크거나 같으므로 새로 추가된 Node를 오른쪽에 위치시킨다.
                    )
                    loop(path.tail(), newSubTree)
                }
            }
            // path를 따라서 모든 Node를 새로 만들어서 쌓인 subTree
            FunStream.Nil -> subTree
        }
    // 여기서 생성되는 Node는 Tree에 새롭게 추가되어야 하는 Node다.
    return loop(path, Node(value, EmptyTree, EmptyTree))
}