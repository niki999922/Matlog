package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class Variable(var node: String) : Node {
    var parentNode: Node? = null
    var parentCount = 0

    override fun node() = "Variable"

    override fun printNode() = node

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun getBReduction(): Node?  = null

    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) {}

    override fun addParentCount() {
        ++parentCount
    }

    override fun getValueParentCount() = parentCount

    override fun renameLambdaVariables() {}

    override fun bReduction() {}

    override fun createCopy(): Node {
        return Variable(node)
    }
}