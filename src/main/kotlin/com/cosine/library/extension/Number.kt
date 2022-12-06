package com.cosine.library.extension

import java.text.DecimalFormat

private const val DEFAULT_FORMAT = "#,##0.###"
private val decimalFormat = DecimalFormat(DEFAULT_FORMAT)

fun Double.format() = decimalFormat.format(this)
fun Int.format() = decimalFormat.format(this)