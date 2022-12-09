package com.cosine.library.util

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.nio.charset.StandardCharsets

class CustomConfig(private val plugin: JavaPlugin, private val fileName: String) {

    private val file: File = File("${plugin.dataFolder}\\${this.fileName}")
    private lateinit var config: YamlConfiguration

    fun loadConfig() {
        config = YamlConfiguration.loadConfiguration(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8))
    }
    fun reloadConfig() {
        config.load(file)
    }
    fun getConfig(): YamlConfiguration {
        return config
    }
    fun saveConfig() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun saveDefaultConfig() {
        if (!file.exists()) {
            plugin.saveResource(fileName, false)
        }
    }
}