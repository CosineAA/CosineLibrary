package com.cosine.library.command

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Argument(
    val argument: String,
    val description: String,
    val isOp: Boolean = false
)

