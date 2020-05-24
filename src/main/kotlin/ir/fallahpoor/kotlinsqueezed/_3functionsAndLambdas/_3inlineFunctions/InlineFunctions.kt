package ir.fallahpoor.kotlinsqueezed._3functionsAndLambdas._3inlineFunctions

/**
 * To get an idea of what 'inlining' means you can refer to
 * https://en.wikipedia.org/wiki/Inline_expansion
 */

/**
 * Using higher-order functions imposes certain runtime penalties: each function is an object, and
 * it captures a closure, i.e. those variables that are accessed in the body of the function. Memory
 * allocations (both for function objects and classes) and virtual calls introduce runtime overhead.
 *
 * But it appears that in many cases this kind of overhead can be eliminated by inlining the lambda
 * expressions.
 *
 * To define an inline function mark the function with the 'inline' modifier.
 */
inline fun <T> lock(body: () -> T) {}

/**
 * The inline modifier affects both the function itself and the lambdas passed to it: all of those
 * will be inlined into the call site.
 */

/**
 * Inlining may cause the generated code to grow; however, if we do it in a reasonable way (i.e.
 * avoiding inlining large functions), it will pay off in performance.
 */

/**
 * In case you want only some of the lambdas passed to an inline function to be inlined, you can mark
 * some of your function parameters with the 'noinline' modifier.
 */
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {}

/**
 * Note that if an inline function has no inlinable parameters and no reified type parameters, the
 * compiler will issue a warning, since inlining such functions is very unlikely to be beneficial.
 */
inline fun bar(i: Int) {}

/***********************
 *  Non-local Returns  *
 ***********************/

/**
 * In Kotlin, we can only use a normal, unqualified return to exit a named function or an anonymous
 * function.
 * This means that to exit a lambda, we have to use a label, and a bare return is forbidden inside a
 * lambda.
 */
fun ordinaryFunction(f: () -> Unit) {}

fun foo() {
    ordinaryFunction {
//        return // ERROR: cannot make `foo` return here
    }
}

/**
 * If the function the lambda is passed to is inlined, the return can be inlined as well, so it
 * is allowed.
 */
inline fun inlinedFun(f: () -> Unit) {}

fun bar() {
    inlinedFun {
        return // OK: the lambda is inlined
    }
}

/**
 * Such returns (located in a lambda, but exiting the enclosing function) are called non-local
 * returns. We are used to this sort of construct in loops, which inline functions often enclose.
 */
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true // returns from hasZeros
    }
    return false
}

/**
 * Note that some inline functions may call the lambdas passed to them as parameters not directly
 * from the function body, but from another execution context, such as a local object or a nested
 * function. In such cases, non-local control flow is also not allowed in the lambdas. To indicate
 * that, the lambda parameter needs to be marked with the 'crossinline' modifier.
 */
inline fun f(crossinline body: () -> Unit) {
    val f = object : Runnable {
        override fun run() = body()
    }
    // ...
}

/*****************************
 *  Reified type parameters  *
 *****************************/

/**
 * Sometimes we need to access a type passed to us as a parameter.
 */
class TreeNode {
    val parent: TreeNode? = null
}

fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

/**
 * Here, we walk up a tree and use reflection to check if a node has a certain type. It’s all fine,
 * but the call site is not very pretty.
 */
val treeNode = TreeNode()
val a = treeNode.findParentOfType(TreeNode::class.java)

/**
 * What we actually want is simply pass a type to this function, i.e. call it like this:
 * treeNode.findParentOfType<TreeNode>()
 *
 * To enable this, inline functions support reified type parameters, so we can write something
 * like the following:
 */
inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

/**
 * We qualified the type parameter with the 'reified' modifier, now it’s accessible inside the
 * function, almost as if it were a normal class. Since the function is inlined, no reflection
 * is needed, normal operators like 'is' and 'as' are working now.
 */

/***********************
 *  Inline Properties  *
 ***********************/

/**
 * Since Kotlin 1.1, The 'inline' modifier can be used on accessors of properties that don't have
 * a backing field.
 * You can annotate individual property accessors or an entire property, which marks both of its
 * accessors as inline.
 */
class Foo
class Bar

val foo: Foo
    inline get() = Foo()

inline var bar: Bar
    get() = Bar()
    set(v) {}

/**************************************************
 *  Restrictions for Public API Inline Functions  *
 **************************************************/

/**
 * When an inline function is public or protected and is not a part of a private or internal
 * declaration, it is considered a module's public API. It can be called in other modules and
 * is inlined at such call sites as well.
 *
 * This imposes certain risks of binary incompatibility caused by changes in the module that
 * declares an inline function in case the calling module is not re-compiled after the change.
 *
 * To eliminate the risk of such incompatibility being introduced by a change in non-public API
 * of a module, the public API inline functions are not allowed to use non-public-API declarations,
 * i.e. private and internal declarations and their parts, in their bodies.
 *
 * An internal declaration can be annotated with @PublishedApi, which allows its use in public API
 * inline functions. When an internal inline function is marked as @PublishedApi, its body is checked
 * too, as if it were public.
 */