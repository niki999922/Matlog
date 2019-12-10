package ru.ifmo

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Lambda
import ru.ifmo.parser.expression.values.Variable
import java.awt.desktop.AppForegroundListener
import java.io.File

object TreePrinter {

    private fun createIdLine(tree: Node, id: Int): String = "$id " +
            "[label=\"${tree.javaClass.name.split('.').last()}  ${if (tree is Variable) {
                tree.node
            } else {
                ""
            }} (deb: ${tree.debugInd()})\"" +
            (if (tree is Variable) ", color=red" else "") +
            "];"

    private fun getId(tree: Node) = System.identityHashCode(tree)

    private fun dfs(tree: Node): String = buildString {
        val id = getId(tree)
        appendln(createIdLine(tree, id))

        if (tree is Application) {
            appendln("$id -> {${getId(tree.leftChild())}};")
            appendln("$id -> {${getId(tree.rightChild())}};")
            append(dfs(tree.leftChild()))
            append(dfs(tree.rightChild()))
        }

        if (tree is Lambda) {
            appendln("$id -> {${getId(tree.leftChild())}};")
            appendln("$id -> {${getId(tree.rightChild())}};")
            append(dfs(tree.leftChild()))
            append(dfs(tree.rightChild()))
        }
    }

    private fun print(tree: Node): String {
        return dfs(tree)
    }

    fun printToFile(tree: Node, name: String = "Lambda") {
        val file = File("$name.dot")
        file.createNewFile()
        file.writeText(
                "digraph $name {${System.lineSeparator()}" +
                        print(tree) +
                        "${System.lineSeparator()}}"
        )
    }
}