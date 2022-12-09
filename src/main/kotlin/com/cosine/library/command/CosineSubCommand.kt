package com.cosine.library.command

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CosineSubCommand(
    val helpPriority: Int = 100,
    val argument: String,
    val description: String = "",
    val isOp: Boolean = false
)

