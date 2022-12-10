package com.cosine.library.extension

import com.cosine.library.CosineLibrary.Companion.plugin
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

fun Player.sendMessages(vararg message: String) = message.forEach { sendMessage(it) }

fun Player.hideAllPlayers() {
    for (players in Bukkit.getOnlinePlayers()) {
        this.hidePlayer(plugin, players)
    }
}

fun Player.showAllPlayers() {
    for (players in Bukkit.getOnlinePlayers()) {
        this.showPlayer(plugin, players)
    }
}

fun getOfflinePlayer(player: String, block: (OfflinePlayer) -> Unit) {
    plugin.server.scheduler.runTaskAsynchronously(plugin) {
        val offlinePlayer = Bukkit.getOfflinePlayer(player)
        block(offlinePlayer)
    }
}