package ru.ifmo.parser.expression.values

import ru.ifmo.parser.Node

data class Variable(var node: String) : Node {
    var parentNode: Node? = null
    var parentCount = 0


    var debug_i = lazy {
        ++Node.debug_ind
    }

    override fun debugInd() = debug_i.value

    override fun node() = "Variable"

    override fun printNode() = node

    override fun leftChild(): Node? = null

    override fun rightChild(): Node? = null

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun getBReduction(): Node?  = null

    override fun openWrapper(listNode: MutableSet<NodeWrapper>):Node = this

    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) {}
    override fun normalizeLambdaLink(lambdaArgument: NodeWrapper) {}

    override fun addParentCount() {
        ++parentCount
    }

    override fun subParentCount() {
        --parentCount
    }

    override fun setValueParentCount(value: Int) {
        parentCount = value
    }

    override fun getValueParentCount() = parentCount

    override fun deleteNaxerWrappers() {}


    override fun renameLambdaVariables() {}

    override fun newRenameLambdaVariables(listNode: MutableMap<String, String>) {
        if (listNode.contains(node)) {
            node = listNode[node].toString()
        }
    }

    override fun bReduction() {}

    override fun oldCreateCopy(listNode: MutableMap<String, NodeWrapper>): Node {
        return Variable(node)
    }

    override fun createCopy(): Node {
        return Variable(node)
    }

}