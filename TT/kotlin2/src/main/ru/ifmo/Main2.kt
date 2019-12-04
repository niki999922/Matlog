package ru.ifmo

import ru.ifmo.parser.Node
import ru.ifmo.parser.expression.ParserLambdaExpression
import ru.ifmo.parser.expression.operations.Application
import ru.ifmo.parser.expression.operations.Lambda
import ru.ifmo.parser.expression.values.NodeWrapper
import ru.ifmo.parser.expression.values.Variable
import kotlin.concurrent.thread

class A {
    companion object {
        var treeMy:Node? = null
    }
}

fun main() {
//    var input = BufferedReader(InputStreamReader(System.`in`)).readLines().joinToString("\n")
//    var tree = parser.parse(input)
    val parser = ParserLambdaExpression()



//    val was = "(\\a.a) d"
//    val was = "\\a.\\b.a b"
//    val was = "\\a.(\\b.(\\a.a)) a"
//    val was = "(\\a.(\\b.\\a.a b) a) (e d)"
//    val was = "((\\a.(\\x.a) c) x)"
//    val was = "(\\a.(\\b.\\a.a b) a) e"
//    val was = "(\\x.x x) ((\\x.x) (\\x.x))"
//    val was = "(\\x.x x x x) ((\\x.x) (\\x.x))"
//    val was = "(\\f.\\x.f (f (f x))) (\\f.\\x.f (f (f x)))"
//    val was = "(\\f.\\x.f (f x)) (\\f.\\x.f (f x))"
//    val was = "(\\a.\\b.a)(\\a.\\b.a)(\\a.\\b.b)"
//    val was = "((a\\bbb.c)d)e f g"
//    val was = "(\\f.f f f) (\\x.x)"
//    val was = "(\\x.(\\a. a a) x x x) ((\\a.a) b)"
//    val was = "(\\x.(\\y.x) x) ((\\a.a) b)"
//    val was = "(\\f.\\x.f) (x) a"
//    val was = "(\\x.x x x x)((\\x.x)(\\x.x))"
//    Painter.draw(parser.parse("\\x.(\\f.\\x.f f x) (\\f.\\x.f f x) x"))


//My StackTrace
//    val was = "a ((\\a.b b) e)"
//    val was = "a ((\\a.a b) e)"
//    val was = "a ((\\a.b a) e)"
//    val was = "a ((\\a.a a) e)"

//    val was = "a b ((\\a.\\b.a b) e)"


// OMEGA
//    val was = "(\\x.x x) (\\x.x x)"



// OK
//    val was = "(\\x.(\\y.x) x) ((\\a.a) b)"
//    val was = "(\\f.\\x.f) (x) a"
//    val was = "(\\x.x x x) (\\y.(\\z.z) a y)"
//    val was = "a a \\k.\\m.\\a.\\o.a \\o.b m \\o.\\p.\\i.a l \\a.m l z \\t.t \\u.\\a.\\k.y t t \\n.y"
//    val was = "(\\x.(\\a.(a e) a) x) (\\y.(\\z.y) a)"
//    val was = "(\\x.(\\x.x) x) a"
//    val was = "(\\x.(\\u.\\v.u) x x x) ((\\z.y) a)"
//    val was = "(\\c.((\\q.c) a) c) ((\\b.b) u)"
//    val was = "(\\b.(\\i.b) s b) ((\\l.l) p)"
//    val was = "(\\n.(\\v.n) r n n) ((\\q.y) a)"
//    val was = "(\\d.d z d d) (\\x.(\\r.x) x)"
//    val was = "(\\w.w w ((\\b.\\b.w) c) w) (\\f.(\\c.f) f v)"
//    val was = "(\\v.(\\c.p (\\p.v)) v v) ((\\a.z) u)"
//    val was = "(\\a.a a) (\\n.w (\\n.(\\t.h) p) n)"
//    val was = "(\\a.a a) (\\n.n (\\v.y))"
//    val was = "\\a.(\\a.\\f.a (\\s.a)) (\\n.\\y.\\v.y (\\v.\\u.u w (\\v.\\n.(\\t.h) (\\y.x)) n))"
//    val was = "(\\p.p p) (\\u.\\m.(u (\\u.m)) u)"
//    val was = "(\\g.(((g (\\w.(\\j.g)))) ((\\h.(g (h (v h))))))) (\\e.((e e) e))"
//    val was = "(\\x.(x a) (x b)) (\\y.(\\z.z) a)"
    val was = "(\\x.(\\u.\\v.u) x x x) ((\\z.y) a)"


//Infinity loop
//    val was = "(\\v.(\\p.p (\\p.v)) v) ((\\a.z) u)"
//    val was = "(\\v.(\\p.p (z p) (\\p.v)) v) (\\n.\\i.n i ((\\a.z) u) )"
//    val was = "(((\\n.((((((\\f.(f o)) (\\t.((\\v.(n (\\d.k))) (\\j.(\\a.(t (\\a.((\\i.(\\r.v)) ((\\p.(\\l.((\\z.g) (\\s.((\\w.((\\e.(\\k.(\\p.(p (\\q.e))))) s)) (\\k.((a (n j)) (\\r.t)))))))) n))))))))) k) r) n) (n (n (\\x.(\\p.(\\h.(\\h.(\\l.(w l)))))))))) (\\e.(((((\\f.(e ((((\\f.(\\d.(x (e ((\\q.q) (\\h.(v u))))))) (\\l.((\\i.i) ((\\d.q) (\\i.g))))) f) (\\c.(\\s.(\\h.(f (\\l.w)))))))) (\\j.(\\p.(\\i.j)))) a) (\\e.(\\b.(e ((\\d.(d n)) n))))) u))) ((\\q.(\\n.(y (\\y.(\\y.((\\b.((\\j.(((\\d.(\\r.(n ((\\n.(\\y.(\\q.(b (((\\f.(b q)) (\\j.(\\w.(\\j.f)))) (y (((\\o.(\\x.((\\c.r) b))) i) q))))))) (\\u.((\\f.((((\\c.n) i) (\\p.((\\w.((j (y ((\\t.(\\k.(b k))) (\\h.(\\i.a))))) (s f))) (\\n.(h w))))) (\\c.(c ((\\c.d) (c d)))))) (\\a.(\\r.q)))))))) n) (((b j) (n (\\g.y))) y))) b)) h)))))) (\\w.(\\n.(\\f.((w (\\w.n)) n))))))"
//    val was = "(((\\w.(((w w) ((\\b.(((b w) w) (\\b.(\\s.(\\r.(\\x.(\\p.(\\d.(((((b o) c) (\\p.p)) (\\j.(\\y.(\\y.w)))) b))))))))) c)) w)) (\\f.(((\\c.f) (f (((f ((\\z.f) (f f))) f) f))) v))) (\\m.m))"
//    val was = "(e (r (j (\\z.(\\h.(((\\v.((\\f.(\\v.(((\\p.(\\c.((((v z) (((p (z p)) (\\p.v)) (\\s.v))) (\\c.((c v) (\\x.(c (v s)))))) (\\l.(((((\\s.(\\q.r)) (\\y.(f f))) f) (l (\\q.((c ((\\w.((((q v) z) (((\\x.((x (\\f.(a (\\s.(\\r.(s l)))))) p)) (((\\s.(((\\k.h) (\\d.z)) c)) ((\\f.c) ((\\g.((((\\e.(\\n.(d (n (\\f.(\\f.(((\\d.((\\o.(f d)) (\\w.(\\r.(m r))))) (\\w.(((\\h.h) (f ((\\t.(\\y.t)) (\\c.(\\s.(z (\\p.(\\w.(\\w.(\\n.v)))))))))) y))) (\\f.z)))))))) v) f) (\\u.((u t) v)))) (\\y.(\\p.((((\\j.m) c) ((\\t.(v q)) (\\t.(\\p.(\\m.((\\p.(q (c p))) ((\\d.(v (\\l.(((m (\\o.(\\b.(\\e.(f (\\d.((\\k.y) t))))))) (\\l.((\\d.(\\o.(\\m.(\\j.(\\s.(\\f.(v (\\q.(s (\\l.(((\\u.((v e) l)) (k l)) l))))))))))) (q ((\\s.(p (((\\b.(\\f.(\\x.(v a)))) a) q))) n))))) ((l m) p))))) (\\b.(w p))))))))) p)))))) (\\q.(q y)))) v)) v)) h)) h)))) ((\\b.(\\h.(h (\\v.((\\e.(p (\\h.h))) (\\y.(\\p.(\\a.((\\u.(s (\\y.(\\y.d)))) (e ((\\v.((\\g.((c (\\j.(y y))) (v ((v (\\p.(\\t.(z v)))) (c ((\\c.(k (((\\x.(\\x.(((c h) p) p))) l) v))) ((e f) a))))))) l)) (\\x.(b (\\y.(y (n (\\k.(h ((\\s.f) ((\\m.(\\a.((\\o.(\\v.(o i))) u))) (\\p.(\\c.(\\s.(\\p.((\\n.(\\x.((\\y.(\\d.(\\q.(y x)))) (p (\\f.(\\m.(\\z.(\\c.((\\w.(((e i) v) p)) (\\o.(\\c.(m j)))))))))))) (\\j.((p (q c)) p))))))))))))))))))))))))))) z)))))) v) (f (((v i) ((\\n.v) v)) (\\u.o)))))) p)) h) (\\n.(\\i.(\\w.(h (\\z.(\\q.(\\d.(h ((n (z (i ((\\a.(z ((i (\\w.x)) (\\j.((\\k.((p (\\e.(\\k.(\\f.(k z))))) (\\c.(q i)))) g))))) u)))) h)))))))))))))))"
//    val was = "(((m (\\h.(\\q.(\\j.(\\o.((f j) (h j))))))) (\\a.((\\i.(((a a) y) ((\\a.(\\f.(a (\\s.(a j))))) (\\n.((a (n (\\y.(\\v.(\\v.y))))) (i (\\v.(((\\u.u) (a (i (a v)))) (((w (\\v.(\\n.(\\o.(\\q.((\\q.((((\\t.h) (\\e.((\\g.((h v) (\\y.(d e)))) n))) (t o)) ((\\y.(\\x.(\\r.((d (\\o.((\\s.(\\c.a)) o))) (\\z.(\\u.(((\\l.(\\i.l)) (\\z.(q p))) q))))))) (\\g.i)))) p)))))) p) n))))))))) a))) (\\v.(\\d.((\\v.f) (\\m.m)))))"
//    val was = "(c ((\\s.(((\\h.(s h)) (r u)) (s s))) (((o ((\\q.(o (\\l.e))) ((p q) s))) (\\t.((\\c.(t ((\\f.(t c)) c))) t))) ((d (((j p) ((\\h.h) ((\\t.(\\q.(\\h.(z q)))) (\\s.((\\m.m) h))))) (\\b.(b (t (\\e.(\\h.(((((\\r.(((\\w.(e (\\c.(\\t.(e (u (\\g.((\\u.(f b)) (w (g r)))))))))) (((e ((\\y.(y l)) (e (\\n.h)))) ((\\l.((\\f.(\\m.(h (\\o.((((\\z.o) r) e) (o ((\\x.l) (m h)))))))) (\\v.((v r) h)))) (\\h.e))) ((\\q.e) (\\u.b)))) r)) ((((h b) h) (\\i.((h b) z))) (\\p.(\\v.((\\k.(v (\\c.(\\w.(\\i.p))))) (\\w.((\\l.((\\q.((\\e.(\\f.p)) (w (\\b.i)))) (v ((\\e.(\\q.(\\y.w))) e)))) e))))))) h) (b h)) e)))))))) (((\\m.(\\d.(\\t.(\\c.(\\w.((\\f.(\\k.((h q) c))) w)))))) (\\f.((i e) (((\\w.(\\l.(w ((f (\\g.((q w) (\\c.(\\c.(\\j.(((((c (\\g.(\\k.(\\d.(\\w.(\\a.((\\v.(\\r.f)) (k (w (\\v.(\\n.(((\\m.(c ((\\z.m) i))) ((\\w.((\\j.(g (w h))) (((\\z.(\\g.e)) ((a w) (\\l.(\\v.w)))) (\\o.(\\f.j))))) g)) g)))))))))))) ((\\k.(\\k.((\\k.((\\p.(\\s.d)) w)) k))) (\\j.((\\i.(b j)) c)))) ((\\q.(\\q.((\\o.(((\\l.(\\h.((z f) (u (\\h.l))))) (\\h.((g ((\\n.(\\t.(\\x.(\\i.(\\d.(((\\m.b) l) (\\e.((\\k.(\\a.((\\i.g) (\\y.((\\p.(\\k.a)) c))))) (m (((\\d.(\\b.(((d (\\k.((\\j.(g e)) d))) (\\f.(\\g.((((\\m.(\\n.g)) x) (d (\\j.(\\z.(\\k.i))))) ((c (g j)) x))))) q))) (\\v.(\\p.(\\c.(\\r.t))))) (c (n (((k (\\b.f)) q) (\\t.h)))))))))))))) (\\p.(\\i.(\\r.(((\\r.((\\q.((\\b.((c q) ((f b) (r (\\g.(\\o.(c (\\o.(\\t.(\\p.e)))))))))) (\\n.(e (r ((\\b.(\\u.(b q))) (\\i.((\\a.w) p)))))))) ((\\v.(\\a.(\\b.((((\\d.(\\a.(r (s a)))) ((\\r.((\\b.(e l)) r)) n)) q) c)))) ((\\s.w) d)))) r) (\\s.(\\c.(r l))))))))) (\\a.j)))) (\\i.((((\\s.(\\i.((((o g) (\\u.((((\\q.(c u)) (\\u.(\\o.w))) q) (c d)))) (q (\\r.g))) f))) (\\s.(((c (\\m.((j k) q))) (\\r.(\\r.l))) c))) (\\y.(\\f.(\\l.(l ((\\v.l) l)))))) i)))) s))) (\\a.((\\l.(((\\y.a) o) f)) (\\t.(\\j.d)))))) (\\m.e)) ((l (\\l.(f (\\h.((f (\\t.(l (j w)))) (f ((m r) (\\k.((\\h.(\\u.((d (\\e.u)) ((((\\h.((\\x.(i (c ((\\h.(\\q.(\\u.(\\n.(\\j.(((\\f.l) h) (t u))))))) ((w g) c))))) c)) (\\r.((h h) c))) (\\f.(\\w.(\\c.(((\\o.(\\b.(((\\j.((\\x.(\\d.(\\o.(h (\\k.(c (f ((j (\\f.(\\u.(\\j.(\\p.(\\z.(\\t.(((g (((\\t.(\\x.((o (\\h.j)) (\\k.((\\q.(((\\k.g) (\\w.(\\x.(\\h.f)))) (\\p.(\\a.j)))) p))))) (\\l.((\\m.(\\x.(\\s.(\\a.(\\k.(\\w.w)))))) k))) (y ((\\u.(\\o.(m (\\l.(\\m.((\\f.((v a) w)) m)))))) (\\c.(((((d j) (\\h.b)) j) h) (\\v.(\\c.h)))))))) d) u)))))))) w)))))))) o)) l) c))) a) (\\z.h)))))) (((\\n.((\\v.(\\i.(((\\u.(\\k.(((((g i) (\\e.n)) (\\b.((f (\\d.(\\i.h))) ((\\j.(\\e.(\\d.(\\i.((b c) (j (\\u.n))))))) u)))) a) (\\i.j)))) v) (\\e.n)))) ((\\h.(\\x.(\\m.((l m) f)))) l))) (\\q.((\\q.(\\o.(\\n.(\\h.(\\n.l))))) u))) j))))) f))))))))) (\\l.(l (\\u.(\\b.p)))))))))))) f)))) f) f)))) (\\e.((\\s.s) e)))))))"

//StackOverflow
//    val was = "(((\\n.((\\w.(n (s n))) (\\t.(t t)))) ((\\d.(((d z) (((i s) (x d)) (\\d.(n d)))) d)) (\\x.((\\w.((\\c.x) (\\g.m))) (((x (\\h.((\\h.(((t h) (\\t.(\\u.(((\\h.(\\p.(\\d.((\\q.(\\e.(\\f.x))) (((x (\\a.(\\o.p))) d) h))))) (\\g.(((\\e.((\\g.((\\i.h) e)) u)) h) ((((j h) h) (g w)) (\\r.((h u) (\\n.r))))))) (\\n.(\\f.h)))))) (\\w.x))) x))) (\\r.x)) x))))) (((\\b.(\\l.(l (((\\b.b) ((l z) (b (l ((\\z.c) (\\a.(\\a.((\\n.(((\\v.((\\c.(\\d.((a v) v))) (n b))) a) (\\t.(((\\y.(\\n.((\\h.(\\g.(\\b.y))) b))) (\\s.(a t))) (b b))))) ((\\n.n) (r (l l))))))))))) (b ((b ((\\j.(b (\\p.(b j)))) (e (\\j.((\\a.(((\\y.(\\y.(l (\\a.((j c) a))))) (\\w.u)) (\\f.b))) b))))) w)))))) b) (\\m.((\\e.(\\s.s)) m))))"
//    val was = "(w ((((\\a.(\\g.g)) i) ((\\u.(e (u (\\g.((\\c.(\\n.(((u g) (\\g.(\\s.(g (x n))))) (g u)))) (((\\s.(h u)) q) (g u))))))) (\\b.(\\c.((\\n.(\\k.(((b ((\\m.k) ((\\g.(\\c.(\\p.(c (\\p.(\\f.(\\n.(\\i.(k v))))))))) (n y)))) n) (\\o.k)))) c))))) (((\\v.((x ((\\v.(\\z.(\\w.f))) ((\\q.((q v) v)) v))) (\\g.(\\i.(\\b.(\\d.((\\x.t) (\\u.(\\j.(e d)))))))))) r) t)))"
//    val was = "(\\a.((\\r.(\\t.(\\h.(\\p.(\\q.((\\q.(\\f.(i (\\x.(\\k.(q (\\c.(c (\\b.(q ((\\h.x) q))))))))))) (\\r.(\\b.(\\x.(\\v.(y (t (\\m.(r (\\n.((h (\\b.((\\f.(((\\v.((b (\\r.(q (\\j.(\\c.(\\g.(h (\\m.s)))))))) (\\a.(\\z.(v (\\l.(\\z.(\\s.(\\c.(\\k.(\\e.(s b)))))))))))) h) (m a))) x))) (\\g.(m ((\\t.(((\\d.(\\c.(\\v.(x ((((\\d.((\\f.(\\m.(\\x.((\\j.(\\k.(\\h.(\\f.(\\s.(r (\\r.(\\r.(\\v.(x (\\j.(\\d.c)))))))))))) (\\e.(\\k.e)))))) (\\b.((\\g.(((\\q.(\\z.(v (r r)))) c) t)) (\\p.(t k)))))) (\\z.(\\p.w))) c) (\\q.((\\g.(\\d.r)) d))))))) v) a)) n))))))))))))))))))) (a (\\i.(\\e.(((a (\\p.(s a))) (i (k (\\n.(a ((n i) ((\\z.(e (\\x.z))) a))))))) (a ((\\q.(\\p.(\\i.q))) (\\o.(\\i.(\\e.(\\u.(\\q.((\\i.(\\m.(\\m.u))) (((\\w.(\\o.(q o))) (\\e.((u e) i))) (i j))))))))))))))))"



//    val was = "(\\x. x x x)(\\x. x x x)\n"
//    val was = "(\\x. x x x x)((\\x. x)(\\x. x))"
//    val was = "(\\v.(\\p.p (\\p.v)) v) ((\\a.z) u)"

//    val was = "(\\x. x x x)(\\x. x x x)\n"

    var tree = parser.parse(was)
    tree = NodeWrapper(tree)
    A.treeMy = tree
//    Painter.draw(tree)
    tree.normalizeLinks(mutableMapOf())
    normalizeRoot(tree)
    A.treeMy = tree

    tree.renameLambdaVariables()
//    Node.indexVariable = 0


    println("was     : $was")
    println("after no: ${tree.printNode()}\n")
//    println("B redux : ${tree.getBReduction()?.printNode() ?: "can't find B redux"}")


//    println("Redex    : ${tree.getBReduction()!!.printNode()}\n")


    println("Starting do B reduction steps:")
    println("tree ${Painter.ind}  : ${tree.printNode()}")
    Painter.draw(tree)
    var redux = tree.getBReduction()
    while (redux != null) {
        Thread.sleep(100)
        redux.bReduction()
        redux = tree.getBReduction()
        println("tree ${Painter.ind}  : ${tree.printNode()}")
        Painter.draw(tree)
    }
    println("\ntree in end: ${tree.printNode()}")
//    println()
//    var tree2 = parser.parse("\\a.\\b.a b c (\\d.e \\f.g) h")
//    println("was: \\a.\\b.a b c (\\d.e \\f.g) h")
//    println("expected: (\\a.(\\b.((((a b) c) (\\d.(e (\\f.g)))) h)))")
//    println("get     : ${tree2.printNode()}")
//    println()
//
//    var tree3 = parser.parse("((a\\bbb.c)d)e \nf g")
//    println("was: ((a\\bbb.c)d)e \nf g")
//    println("expected: (((((a (\\bbb.c)) d) e) f) g)")
//    println("get     : ${tree3.printNode()}")
}


fun normalizeRoot(node : Node) {
    if (node is Application) {
        node.leftChild().setParent(node)
        node.rightChild().setParent(node)
    }
    if (node is Lambda) {
        node.leftChild().setParent(node)
        node.rightChild().setParent(node)
    }
    if (node is NodeWrapper) {
        node.leftChild().setParent(node)
    }
}