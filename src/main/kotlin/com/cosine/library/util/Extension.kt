package com.cosine.library.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

fun Player.sendMessages(vararg message: String) = message.forEach { sendMessage(it) }

fun Player.hideAllPlayers(plugin: JavaPlugin) {
    for (players in Bukkit.getOnlinePlayers()) {
        this.hidePlayer(plugin, players)
    }
}

fun Player.showAllPlayers(plugin: JavaPlugin) {
    for (players in Bukkit.getOnlinePlayers()) {
        this.showPlayer(plugin, players)
    }
}

fun String.applyColor(): String = ChatColor.translateAlternateColorCodes('&', this)

fun MutableList<String>.applyColors(): MutableList<String> {
    return this.map(String::applyColor).toMutableList()
}

fun String.removeColor() = ChatColor.stripColor(this)

fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

fun String.isNotInt(): Boolean {
    return try {
        this.toInt()
        false
    } catch (e: NumberFormatException) {
        true
    }
}