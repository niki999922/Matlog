package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node

class Lambda(private var left: String, private var right: Node): Node {
    override fun node()= "Lambda"

    override fun leftChild(): Node? = null

    override fun rightChild() = right

    override fun printNode() = "(\\$left.${right.printNode()})"
}