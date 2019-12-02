package ru.ifmo

import ru.ifmo.parser.Node
import java.io.File

object Painter {
    var ind = 0
    private fun draw(name: String) {
        ProcessBuilder("dot", "-Tpng", "$name.dot")
            .redirectOutput(File("$name$ind.png"))
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
        ind++
    }

    fun draw(tree: Node, name: String = "Lambda") {
        TreePrinter.printToFile(tree)
        draw(name)
    }
}