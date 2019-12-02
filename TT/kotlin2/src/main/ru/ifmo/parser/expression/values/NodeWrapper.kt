package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class NodeWrapper(var node: Node) : Node {
    var parentNode: Node? = null
    var debug_i = lazy {
        ++Node.debug_ind
    }
    override fun debugInd() = debug_i.value

    override fun node() = "Variable Wrapper"

    override fun printNode() = node.printNode()

    override fun leftChild() = node

    override fun rightChild(): Node? = null

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {parentNode = node}


    override fun getBReduction(): Node?  = node.getBReduction()

    override fun openWrapper(listNode: MutableSet<NodeWrapper>):Node {
        node = node.openWrapper(listNode)
        return this
    }

    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) = node.normalizeLinks(listNode)

    override fun renameLambdaVariables() = node.renameLambdaVariables()

    override fun bReduction() {}
}