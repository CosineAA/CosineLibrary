package com.cosine.library.command

data class CosineCommandData(
    val command: String,
    val subCommand: String?,
    val line: String,
    val description: String
) {
    val isSubCommand = subCommand != null
}