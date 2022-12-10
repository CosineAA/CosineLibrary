package com.cosine.library.extension

import org.bukkit.ChatColor

fun String.applyColor(): String = ChatColor.translateAlternateColorCodes('&', this)

fun MutableList<String>.applyColors(): MutableList<String> {
    return this.map(String::applyColor).toMutableList()
}

fun String.removeColor() = ChatColor.stripColor(this)

fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.isNotInt(): Boolean {
    return try {
        this.toInt()
        false
    } catch (e: NumberFormatException) {
        true
    }
}