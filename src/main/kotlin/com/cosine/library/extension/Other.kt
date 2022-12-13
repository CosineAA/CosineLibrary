package com.cosine.library.extension

import com.cosine.library.CosineLibrary.Companion.plugin
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

fun later(delay: Int = 1, block: () -> Unit) = plugin.server.scheduler.runTaskLater(plugin, block, delay.toLong())