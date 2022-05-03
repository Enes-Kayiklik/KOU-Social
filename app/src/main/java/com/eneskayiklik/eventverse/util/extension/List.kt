package com.eneskayiklik.eventverse.util.extension

fun Int.numbersAsList(): List<Int> {
    var temp = this
    val result = mutableListOf<Int>()
    while (temp != 0) {
        result.add(temp % 10)
        temp /= 10
    }
    return result
}