package com.cosine.library.extension

import com.cosine.library.CosineLibrary.Companion.plugin
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.util.Consumer
import java.util.UUID

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

fun getOfflinePlayer(name: String, consumer: Consumer<OfflinePlayer>) {
    plugin.server.scheduler.runTaskAsynchronously(plugin) {
        val offlinePlayer = Bukkit.getOfflinePlayer(name)
        consumer.accept(offlinePlayer)
    }
}

fun getOfflinePlayer(uuid: UUID, consumer: Consumer<OfflinePlayer>) {
    plugin.server.scheduler.runTaskAsynchronously(plugin) {
        val offlinePlayer = Bukkit.getOfflinePlayer(uuid)
        consumer.accept(offlinePlayer)
    }
}