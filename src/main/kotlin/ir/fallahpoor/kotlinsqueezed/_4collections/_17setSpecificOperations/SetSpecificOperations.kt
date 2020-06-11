package ir.fallahpoor.kotlinsqueezed._4collections._17setSpecificOperations

fun main() {

    /**
     * To merge two collections into one, use the union() function. It can be used in the infix form
     * a union b.
     */
    val set1 = setOf("one", "two", "three", "four")
    val set2 = setOf("four", "five", "six")

    println("Set1: $set1")
    println("Set2: $set2")
    println("Set1 union Set2: ${set1 union set2}")

    /**
     * To find an intersection between two collections (elements present in both of them), use intersect().
     */
    println("Set1 intersect Set2: ${set1 intersect set2}")

    /**
     * To find collection elements not present in another collection, use subtract().
     */
    println("Set1 subtract Set2: ${set1 subtract set2}")

}