package ru.ifmo.parser

import ru.ifmo.parser.expression.values.NodeWrapper


interface Node {
    fun node(): String
    fun leftChild() : Node?
    fun rightChild() : Node?
    fun parent(): Node?
    fun setParent(node : Node)
    fun printNode() : String
    fun getBReduction(): Node?
    fun openWrapper(listNode: MutableSet<NodeWrapper>): Node
    fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>)
    fun renameLambdaVariables()
    fun bReduction()

    fun debugInd(): Int

    companion object {
        var indexVariable = 0
        var debug_ind = 0
    }
}