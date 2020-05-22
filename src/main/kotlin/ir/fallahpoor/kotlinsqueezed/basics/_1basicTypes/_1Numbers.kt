package ir.fallahpoor.kotlinsqueezed.basics._1basicTypes

fun main() {

    /**
     * Numerical types in Kotlin are as follow.
     */
    val b: Byte = 100     // 8 bits
    val s: Short = 143    // 16 bits
    val i: Int = 3433     // 32 bits
    val l: Long = 40305   // 64 bits
    val f: Float = 3.14F    // 32 bits, single precision, 6-7 decimal digits
    val d: Double = 4.54554 // 64 bits, double precision, 15-16 decimal digits

    /**
     * Since Kotlin 1.1 there are unsigned versions of [Byte], [Short], [Int], and [Long] too.
     * To denote a numeric value as unsigned we append letter 'u' to it.
     *
     * NOTE: As of now (Kotlin 1.3.72) unsigned numeric types are EXPERIMENTAL.
     */
    val ub: UByte = 255u
    val us: UShort = 65535u
    val ui: UInt = 12345u
    val ul: ULong = 12345678u

    /**
     * All variables initialized with integer values not exceeding the maximum value of [Int]
     * have the inferred type [Int]. If the initial value exceeds this value, then the type
     * is [Long]. For variables initialized with fractional numbers, the compiler infers the
     * [Double] type.
     */
    val one = 1                   // Int
    val threeBillion = 3000000000 // Long
    val oneLong = 1L              // Long
    val oneByte: Byte = 1
    val pi = 3.14                 // Double
    val e = 2.7182818284          // Double
    val eFloat = 2.7182818284f    // Float, actual value is 2.7182817

    /**
     * Unlike some other languages, there are no implicit widening conversions for numbers in
     * Kotlin. For example, a function with a [Double] parameter can be called only on [Double]
     * values, but not [Float], [Int], or other numeric values.
     * Each numeric type has methods to convert it to other numeric types.
     */
    val someInt = 12
//    val someDouble: Double = someInt // Type mismatch
    val someDouble: Double = someInt.toDouble()

    /**
     * You can use underscores to make number constants more readable.
     */
    val oneMillion = 1_000_000
    val creditCardNumber = 1234_5678_9012_3456L
    val socialSecurityNumber = 999_99_9999L
    val hexBytes = 0xFF_EC_DE_5E
    val bytes = 0b11010010_01101001_10010100_10010010

    // REPRESENTATION
    /**
     * On the Java platform, numbers are physically stored as JVM primitive types, unless we need
     * a nullable number reference (e.g. Int?) or generics are involved. In the latter cases numbers
     * are boxed.
     */

    // OPERATIONS

    val addition = 5 + 2
    val subtraction = 5 - 2
    val multiplication = 5 * 2
    val division = 5 / 2 // result => 2
    val modulo = 5 % 2

    val shiftLeft = 1 shl 2
    val shiftRight = 1 shr 2
    val unsignedShiftRight = 1 ushr 2
    val and = 1 and 2
    val or = 1 or 2
    val xor = 1 xor 2
    val not = 2.inv()

}