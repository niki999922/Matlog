package ru.ifmo.parser.expression.values

import ru.ifmo.Painter
import ru.ifmo.parser.Node

data class NodeWrapper(var node: Node) : Node {
    var parentCount = 0

    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Variable Wrapper"

    override fun printNode():String {
        while (node is NodeWrapper) {
            node = (node as NodeWrapper).node
        }

        return node.printNode()
    }

    override fun leftChild() = node

    override fun rightChild(): Node? = null

    override fun addParentCount() {
        ++parentCount
    }

    override fun getBReduction(nodeTmp: NodeWrapper): Node? {
        while (node is NodeWrapper) {
            node = (node as NodeWrapper).node
        }

        nodeTmp.node = this
        return node.getBReduction(nodeTmp)
    }

    override fun createCopy(): Node {
        var copy = node.createCopy()
        return copy
    }

    override fun getValueParentCount() = parentCount

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        node.normalizeNamesLambda(listName)
        node.addParentCount()
    }

    override fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper) {
        node.setWrapperInVariable(name,nodeWrapper)
    }

    override fun bReduction(parent: Node) {}
}