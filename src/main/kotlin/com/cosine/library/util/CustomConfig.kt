package com.cosine.library.util

import com.cosine.library.item.toByteArrayCompress
import com.cosine.library.item.toItemArrayDecompress
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.*
import java.nio.charset.StandardCharsets

class CustomConfig(private val plugin: JavaPlugin, private val fileName: String) {

    private val file: File = File("${plugin.dataFolder}\\${this.fileName}")
    private lateinit var config: YamlConfiguration
    private val cacheObjectMap = HashMap<String, Array<ItemStack?>>()

    fun loadConfig() { config = YamlConfiguration.loadConfiguration(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)) }

    fun reloadConfig() = config.load(file)

    fun getConfig(): YamlConfiguration = config

    @Suppress("UNCHECKED_CAST")
    fun getItemArray(key: String, cache: Boolean = false): Array<ItemStack>? {
        if(cacheObjectMap.containsKey(key)) return cacheObjectMap[key]!!.map { it?: ItemStack(Material.AIR) }.toTypedArray()
        return if(config.isSet(key)) {
            val items = Base64Coder.decodeLines(config.getString(key)).toItemArrayDecompress()
            if (cache) cacheObjectMap[key] = items.map { if(it.typeId < 0) null else it }.toTypedArray()
            items
        } else null
    }

    fun setItemArray(key: String, items: Array<ItemStack?>, cache: Boolean = false) {
        if(cache)
            object: BukkitRunnable() {
                override fun run() {
                    cacheObjectMap[key] = items
                }
            }.runTaskAsynchronously(plugin)
        else getConfig().set(key, Base64Coder.encodeLines(items.toByteArrayCompress()))
    }

    fun clearCacheMap(async: Boolean = false, after: ()->Unit = {}) {
        if(async) plugin.server.scheduler.runTaskAsynchronously(plugin) { clearCache(after) }
        else clearCache(after)
    }

    private fun clearCache(after: ()->Unit) {
        getConfig().apply {
            cacheObjectMap.forEach { (key, value) -> set(key, Base64Coder.encodeLines(value.toByteArrayCompress())) }
            after()
        }
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