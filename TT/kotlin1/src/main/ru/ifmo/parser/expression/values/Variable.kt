package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class Variable(private val node: String) : Node {
    override fun node() = node

    override fun printNode() = node

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null
}