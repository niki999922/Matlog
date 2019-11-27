package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class Variable(private val node: String) : Node {
    val code: Int = node.hashCode() * 37 % 429496729

    override fun node() = node

    override fun printNode() = node

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null
}