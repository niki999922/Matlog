package ru.ifmo.parser.expression.values

import ru.ifmo.Painter
import ru.ifmo.parser.Node

data class NodeWrapper(var node: Node) : Node {
    var parentNode: Node? = null
    var parentCount = 0

    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Variable Wrapper"

    override fun printNode():String {
        while (node is NodeWrapper && node.getValueParentCount() == parentCount) {
            (node as NodeWrapper).node.setParent(this)
            node = (node as NodeWrapper).node
        }

        return node.printNode()
    }

    override fun leftChild() = node

    override fun rightChild(): Node? = null

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun addParentCount() {
        ++parentCount
    }

    override fun subParentCount() {
        --parentCount
    }

    override fun setValueParentCount(value: Int) {
        parentCount = value
    }

    override fun getBReduction(): Node? {
        while (node is NodeWrapper && node.getValueParentCount() == parentCount) {
            (node as NodeWrapper).node.setParent(this)
            node = (node as NodeWrapper).node
        }
        return node.getBReduction()
    }

    override fun createCopy(): Node {
        var copy = node.createCopy()
        copy.setParent(this)
        return copy
    }

    override fun getValueParentCount() = parentCount

    override fun deleteNaxerWrappers() {
        node.deleteNaxerWrappers()
    }

    override fun normalizeNamesLambda(listName: MutableMap<String, String>) {
        node.normalizeNamesLambda(listName)
        node.addParentCount()
    }

    override fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper) {
        node.setWrapperInVariable(name,nodeWrapper)
    }

    override fun bReduction() {}
}