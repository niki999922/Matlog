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
    fun addParentCount()
    fun subParentCount()
    fun setValueParentCount(value: Int)
    fun getValueParentCount():Int
    fun deleteNaxerWrappers()
    fun normalizeNamesLambda(listName: MutableMap<String, String>)
    fun setWrapperInVariable(name: String, nodeWrapper: NodeWrapper)
    fun debugInd():Int

    companion object {
        var indexVariable = 0
        var debug_ind = 0
    }
}