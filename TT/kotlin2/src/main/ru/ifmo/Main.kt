package ru.ifmo

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Lambda
import ru.ifmo.parser.expression.values.NodeWrapper
import ru.ifmo.parser.expression.values.Variable
import kotlin.concurrent.thread


fun main() {
//    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
//    var tree = parser.parse(input)
    val parser = ParserLambdaExpression()

//    val was = "(\\a.a) d"
//    val was = "a b ((\\a.\\b.a b) e)"
//    val was = "\\a.\\b.a b"
//    val was = "\\a.(\\b.(\\a.a)) a"
//    val was = "(\\a.(\\b.\\a.a b) a) (e d)"
//    val was = "((\\a.(\\x.a) c) x)"
//    val was = "(\\a.(\\b.\\a.a b) a) e"
    val was = "(\\x.x x) ((\\x.x) (\\x.x))"
//    val was = "(\\x.x x x x) ((\\x.x) (\\x.x))"
    var tree = parser.parse(was)
    tree = NodeWrapper(tree)
    tree.normalizeLinks(mutableMapOf())
    normalizeRoot(tree)

    tree.renameLambdaVariables()
    Node.indexVariable = 0


    println("was     : $was")
    println("after no: ${tree.printNode()}\n")
//    println("B redux : ${tree.getBReduction()?.printNode() ?: "can't find B redux"}")


//    println("Redex    : ${tree.getBReduction()!!.printNode()}\n")


    println("Starting do B reduction steps:")
    println("tree ${Painter.ind}  : ${tree.printNode()}")
    Painter.draw(tree)
    var redux = tree.getBReduction()
    while (redux != null) {
        Thread.sleep(1000)
        redux.bReduction()
        println("tree ${Painter.ind}  : ${tree.printNode()}")
        Painter.draw(tree)
        redux = tree.getBReduction()
    }
    println("\ntree in end: ${tree.printNode()}")
//    println()
//    var tree2 = parser.parse("\\a.\\b.a b c (\\d.e \\f.g) h")
//    println("was: \\a.\\b.a b c (\\d.e \\f.g) h")
//    println("expected: (\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))")
//    println("get     : ${tree2.printNode()}")
//    println()
//
//    var tree3 = parser.parse("((a\\bbb.c)d)e \nf g")
//    println("was: ((a\\bbb.c)d)e \nf g")
//    println("expected: (((((a (\\bbb.c)) d) e) f) g)")
//    println("get     : ${tree3.printNode()}")
}


fun normalizeRoot(node : Node) {
    if (node is Application) {
        node.leftChild().setParent(node)
        node.rightChild().setParent(node)
    }
    if (node is Lambda) {
        node.leftChild().setParent(node)
        node.rightChild().setParent(node)
    }
    if (node is NodeWrapper) {
        node.leftChild().setParent(node)
    }
}