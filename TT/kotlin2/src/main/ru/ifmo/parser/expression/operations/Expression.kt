package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node

class Expression(private var son: Node): Node {
    override fun node()= "Expression"

    override fun leftChild() = son

    override fun rightChild(): Node? = null

    override fun printNode() = "${son.printNode()}"
}