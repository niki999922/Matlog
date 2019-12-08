package ru.ifmo.parser

interface Parse {
    fun parse(input: String): Node
}