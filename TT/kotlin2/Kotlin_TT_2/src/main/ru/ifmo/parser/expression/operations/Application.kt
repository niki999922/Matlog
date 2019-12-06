package ru.ifmo.parser.expression.operations

import ru.ifmo.Painter
import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.values.Variable
import ru.ifmo.parser.expression.values.NodeWrapper

class Application(var left: Node, var right: Node) : Node {
    var parentNode: Node? = null
    var parentCount = 0

    override fun node() = "Application"

    override fun leftChild() = left

    override fun rightChild() = right

    override fun parent(): Node? = parentNode

    override fun setParent(node: Node) {
        parentNode = node
    }

    override fun addParentCount() {
        ++parentCount
    }


    override fun printNode(): String {
        while (left is NodeWrapper && left.getValueParentCount() == parentCount) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == parentCount) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }

        return "(${left.printNode()} ${right.printNode()})"
    }

    override fun getBReduction(): Node? {
        while (left is NodeWrapper && left.getValueParentCount() == 1) {
            (left as NodeWrapper).node.setParent(this)
            left = (left as NodeWrapper).node
        }

        while (right is NodeWrapper && right.getValueParentCount() == 1) {
            (right as NodeWrapper).node.setParent(this)
            right = (right as NodeWrapper).node
        }

        if (left is Lambda) {
            var copy = (left as Lambda).createCopy()
            copy.setParent(this)
            left = copy
            left.addParentCount()
            left.normalizeLinks(mutableMapOf())
            left.renameLambdaVariables()//delete
            return this
        }

        if (left is NodeWrapper) {
            var l = left as NodeWrapper
            while (l.node is NodeWrapper) {
                l = l.node as NodeWrapper
            }
            if (l.node is Lambda) {
                var copy = (left as NodeWrapper).node.createCopy()
                copy.setParent(this)
                left = copy
                left.addParentCount()
                left.normalizeLinks(mutableMapOf())
                left.renameLambdaVariables()//delete
                return this
            }
        }
        return left.getBReduction() ?: right.getBReduction()
    }

    override fun createCopy(): Node {
        var l = left.createCopy()
        var r = right.createCopy()
        l.setParent(this)
        r.setParent(this)

        return Application(l, r)
    }

    override fun getValueParentCount() = parentCount

    override fun normalizeLinks(listNode: MutableMap<String, NodeWrapper>) {
        if (left is Variable) {
            val leftVar = left as Variable
            listNode[leftVar.printNode()].let {
                if (it == null) return@let
                if (leftVar.printNode() == it.leftChild().printNode()) {
                    left = it
                    left.addParentCount()

                }
            }
        } else {
            left.setParent(this)
            left.normalizeLinks(listNode)
            left.addParentCount()
        }


        if (right is Variable) {
            val rightVar = right as Variable
            listNode[rightVar.printNode()].let {
                if (it == null) return@let
                if (rightVar.printNode() == it.leftChild().printNode()) {
                    right = it
                    right.addParentCount()
                }
            }
        } else {
            right.normalizeLinks(listNode)
            right.setParent(this)
            right.addParentCount()
        }
    }

    override fun renameLambdaVariables() {
        left.renameLambdaVariables()
        right.renameLambdaVariables()
    }

    override fun bReduction() {
        val leftLambda = left as Lambda
        val lambdaArgWrapper = (leftLambda.leftChild() as NodeWrapper)

        if (right is NodeWrapper && ((right as NodeWrapper).node is NodeWrapper)) {
            var r1 = (right as NodeWrapper)
            var r2 = ((right as NodeWrapper).node as NodeWrapper)
            r2.setParent(this)
            right = r2
            (right as NodeWrapper).node.setParent(lambdaArgWrapper) //????
            lambdaArgWrapper.node = (right as NodeWrapper).node
        } else {
            right.setParent(lambdaArgWrapper)
            lambdaArgWrapper.node = right
        }

        if (parentNode != null) {
            if (parentNode is NodeWrapper) {
                var prevPar = parentNode as NodeWrapper
                (parentNode as NodeWrapper).node = leftLambda.right
                leftLambda.rightChild().setParent(prevPar)
                return
            }
            if (parentNode is Application) {
                var prevApl = parentNode as Application
                if ((parentNode as Application).leftChild() === this) {
                    (parentNode as Application).left = leftLambda.rightChild()
                } else {
                    (parentNode as Application).right = leftLambda.rightChild()
                }
                leftLambda.rightChild().setParent(prevApl)
                return
            }
            if (parentNode is Lambda) {
                var prevLam = parentNode as Lambda
                (parentNode as Lambda).right = leftLambda.rightChild()
                leftLambda.rightChild().setParent(prevLam)
                return
            }
        } else {
            println("Wow b redux from root")
        }
    }
}