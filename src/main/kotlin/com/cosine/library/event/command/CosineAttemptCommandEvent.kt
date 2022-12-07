package com.cosine.library.event.command

import com.cosine.library.command.CosineCommandData
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

class CosineAttemptCommandEvent(
    who: Player,
    command: CosineCommandData
): CosineCommandEvent(who, command), Cancellable {

    private var cancel = false

    override fun isCancelled(): Boolean = cancel
    override fun setCancelled(cancel: Boolean) { this.cancel = cancel }

}