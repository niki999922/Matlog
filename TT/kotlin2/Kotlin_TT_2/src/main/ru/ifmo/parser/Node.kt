package ru.ifmo.parser

import ru.ifmo.parser.expression.values.NodeWrapper


interface Node {
    fun node(): String
    fun leftChild() : Node?
    fun rightChild() : Node?
    fun parent(): Node?
    fun setParent(node : Node)
    fun printNode() : String
    fun bReduction()
    fun createCopy() : Node
    fun getBReduction(): Node?
    fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>)
    fun renameLambdaVariables()
    fun addParentCount()
    fun getValueParentCount():Int

    companion object {
        var indexVariable = 0
    }
}