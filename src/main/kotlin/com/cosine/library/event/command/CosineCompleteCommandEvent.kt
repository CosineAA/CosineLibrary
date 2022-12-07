package com.cosine.library.event.command

import com.cosine.library.command.CosineCommandData
import org.bukkit.entity.Player

class CosineCompleteCommandEvent(
    who: Player,
    command: CosineCommandData
): CosineCommandEvent(who, command) {}