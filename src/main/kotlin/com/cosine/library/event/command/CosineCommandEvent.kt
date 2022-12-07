package com.cosine.library.event.command

import com.cosine.library.command.CosineCommandData
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

abstract class CosineCommandEvent(
    who: Player,
    protected val command: CosineCommandData
): PlayerEvent(who) {

    companion object {
        private val HANDLER_LIST = HandlerList()
        @JvmStatic fun getHandlerList(): HandlerList = HANDLER_LIST
    }

    override fun getHandlers(): HandlerList = getHandlerList()

    fun getCommand() = command.command
    fun isSubCommand() = command.isSubCommand
    fun getLine() = command.line
    fun getDescription() = command.description
    fun getSubCommand() = command.subCommand

}