package com.cosine.library.extension

import java.lang.IllegalArgumentException
import java.util.AbstractMap
import kotlin.math.ln
import kotlin.random.Random

val random: Random by lazy { Random }

fun Double.percent(): Boolean {
    return if (this >= 100.0) true
    else if(this <= 0.0) false
    else {
        val suc = this
        val fail = 100.0 - suc
        mapOf( true to suc, false to fail ).percent()
    }
}

fun Int.percent(): Boolean = toDouble().percent()

inline fun <reified T> Map<T, Double>.percent(): T {
    val entry = entries.stream()
    return entry
        .map { e -> AbstractMap.SimpleEntry(e.key, -ln(random.nextDouble()) / e.value) }
        .min(compareBy { it.value} )
        .orElseThrow { IllegalArgumentException() }.key
}