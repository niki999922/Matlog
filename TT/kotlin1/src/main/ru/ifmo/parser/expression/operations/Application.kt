package ru.ifmo.parser.expression.operations

import ru.ifmo.parser.Node

class Application(private var left: Node?, private var right: Node?): Node {
    override fun node()= "Application"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun printNode() = "(${left!!.printNode()} ${right!!.printNode()})"
}