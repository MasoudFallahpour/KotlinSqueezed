package ir.fallahpoor.kotlinsqueezed._1basics._1basicTypes

fun main() {

    /**
     * Characters are represented by the type Char. Character literals go in single quotes like 'a'.
     * Like numbers, characters are boxed when a nullable reference is needed.
     */

    val c: Char = 'a'
    val newLine: Char = '\n'
    val unicodeChar: Char = '\uFF00'

    println(c)
    println(newLine)
    println(unicodeChar)

}