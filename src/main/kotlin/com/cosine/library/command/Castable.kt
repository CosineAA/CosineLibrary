package com.cosine.library.command

interface Castable<T> {
    fun cast(string: String?): T?
}