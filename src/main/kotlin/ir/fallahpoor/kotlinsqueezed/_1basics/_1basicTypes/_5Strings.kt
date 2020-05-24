package ir.fallahpoor.kotlinsqueezed._1basics._1basicTypes

fun main() {

    /**
     * Strings are represented by the type String. Strings are immutable.
     */
    val s: String = "Hi folks"

    /**
     * Kotlin has two types of string literals: escaped strings that may have escaped characters
     * in them and raw strings that can contain newlines and arbitrary text.
     */
    val escapedString: String = "Hello, World\n"
    print(escapedString)

    val rawString1: String =
        """
        Hi folks,
        Hope y'll are alive and kicking
        """
    println(rawString1)

    val rawString2: String =
        """
        Hi folks,
        Hope y'll are alive and kicking
        """.trimIndent()
    println(rawString2)

    /**
     * String literals may contain template expressions, i.e. pieces of code that are evaluated
     * and whose results are concatenated into the string.
     */
    val i = 10
    println("i = $i")

    val hello = "Hello"
    println("Length of $hello is ${hello.length}")

}