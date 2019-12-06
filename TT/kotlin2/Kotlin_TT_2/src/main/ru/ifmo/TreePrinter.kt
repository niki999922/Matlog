package ru.ifmo

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Lambda
import ru.ifmo.parser.expression.values.NodeWrapper
import ru.ifmo.parser.expression.values.Variable
import java.awt.desktop.AppForegroundListener
import java.io.File

object TreePrinter {

    private fun createIdLine(tree: Node, id: Int): String = "$id " +
            "[label=\"${tree.javaClass.name.split('.').last()} (p: ${tree.getValueParentCount()}) ${if (tree is Variable) {
                tree.node
            } else {
                ""
            }} " +
            (if (tree is Variable) ", color=red" else "") +
            "];"

    private fun getId(tree: Node) = System.identityHashCode(tree)

    private fun getChildrenLine(tree: Node) = buildString {
        if (tree is Application) {
//            appendln("$id -> {${getId(tree.leftChild())}};")
//            appendln("$id -> {${getId(tree.rightChild())}};")

            append(getId(tree.leftChild()))
            append(", ")
            append(getId(tree.rightChild()))
        }

        if (tree is Lambda) {
//            appendln("$id -> {${getId(tree.leftChild())}};")
//            appendln("$id -> {${getId(tree.rightChild())}};")
            append(getId(tree.leftChild()))
            append(", ")
            append(getId(tree.rightChild()))
        }

        if (tree is NodeWrapper) {
//            appendln("$id -> {${getId(tree.leftChild())}};")
            append(getId(tree.leftChild()))
        }


//        appendln("$id -> {${tree.leftChild()}};")


//        tree.childs().forEach {
//            append(getId(it))
//            if (it != tree.childs().last()) {
//                append(", ")
//            }
//        }
    }

    private fun dfs(tree: Node): String = buildString {
        val id = getId(tree)
        appendln(createIdLine(tree, id))
//        append("${getChildrenLine(tree, id)}")

//        appendln("$id -> {${getChildrenLine(tree)}};")
        if (tree is Application) {
            appendln("$id -> {${getId(tree.leftChild())}};")
            appendln("$id -> {${getId(tree.rightChild())}};")
//            if (tree.parentNode !is Lambda) {
//                append(dfs(tree.leftChild()))
//            }
//            if (tree.parentNode !is Lambda) {
//                append(dfs(tree.rightChild()))
//            }
            append(dfs(tree.leftChild()))
            append(dfs(tree.rightChild()))
        }
        if (tree is Lambda) {
            appendln("$id -> {${getId(tree.leftChild())}};")
            appendln("$id -> {${getId(tree.rightChild())}};")
            append(dfs(tree.leftChild()))
//            if (tree.right !== tree.left) {
                append(dfs(tree.rightChild()))
//            }
        }

        if (tree is NodeWrapper) {
            appendln("$id -> {${getId(tree.leftChild())}};")
            append(dfs(tree.leftChild()))
        }


//        tree.childs().forEach {
//            append(dfs(it))
//        }
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