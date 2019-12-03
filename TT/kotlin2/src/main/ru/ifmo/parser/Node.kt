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
    fun newRenameLambdaVariables(listNode: MutableMap<String, String>)
    fun createCopy() : Node

    fun getBReduction(): Node?



    /**
     * depricated
     */
    fun renameLambdaVariables()
    fun openWrapper(listNode: MutableSet<NodeWrapper>): Node
    fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>)
    fun oldCreateCopy(listNode: MutableMap<String, NodeWrapper>) : Node


    /**
     * debug
     */
    fun debugInd(): Int


    companion object {
        var indexVariable = 0
        var debug_ind = 0
    }
}