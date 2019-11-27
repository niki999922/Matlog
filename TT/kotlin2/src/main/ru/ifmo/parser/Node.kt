package ru.ifmo.parser

interface Node {
    fun node(): String
    fun leftChild() : Node?
    fun rightChild() : Node?
    fun printNode() : String
}