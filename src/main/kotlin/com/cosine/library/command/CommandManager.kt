package com.cosine.library.command

object CommandManager {

    fun registerAdapter(vararg adapter: ArgumentAdapter<*>) = adapter.forEach(ArgumentAdapter.Companion::registerAdapter)
    fun registerCommand(vararg command: CosineCommand) = command.forEach(CosineCommand::register)

}