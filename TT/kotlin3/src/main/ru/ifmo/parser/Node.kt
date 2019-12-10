package ru.ifmo.parser

import ru.ifmo.SystemTypes
import ru.ifmo.SystemTypes.systemTypeMap
import ru.ifmo.parser.expression.values.Variable


interface Node {
    var type: Node?

    fun node(): String
    fun leftChild(): Node?
    fun rightChild(): Node?

    fun printOriginalNode(): String
    fun printNode(): String

    //from third task
    fun printType(): String
    fun printProof(listHypothesis: MutableList<Variable>, depth:Int)
    fun initSystem()
    fun containVariable(other: Variable): Boolean
    fun openTypeVariable(other: Variable, T: Node)
    fun problemOneVariable(it: Node):String {
        return it.type!!.printType()
    }

    override operator fun equals(other: Any?): Boolean

    //from second task
    fun normalizeNamesLambda(listName: MutableMap<String, String>)
    fun debugInd(): Int

    companion object {
        var indexVariable = 1
        var typeIndexVariable = 1
        var debug_ind = 1

        fun generateName():String {
            val newName = "v${indexVariable}"
            ++indexVariable
            return newName
        }

        fun generateTypeName():String {
            val newName = "t${typeIndexVariable}"
            ++typeIndexVariable
            return newName
        }
    }

}