package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class Variable(var node: String) : Node {
    var parentCount = 0

    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Variable"

    override fun printNode() = node

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null

    override fun getBReduction(nodeTmp: NodeWrapper): Node?  = null

    override fun addParentCount() {
        ++parentCount
    }

    override fun getValueParentCount() = parentCount

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        node = listName[node] ?: node
    }

    override fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper) {}

    override fun bReduction(parent: Node) {}

    override fun createCopy(): Node {
        return Variable(node)
    }

}