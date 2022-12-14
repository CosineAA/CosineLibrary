package com.cosine.library

import com.cosine.library.command.ArgumentAdapter
import com.cosine.library.command.Castable
import com.cosine.library.command.CommandManager
import com.cosine.library.inventory.InventoryListener
import com.cosine.library.packet.PacketSign
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class CosineLibrary: JavaPlugin() {

    companion object {
        internal lateinit var plugin: CosineLibrary
    }

    override fun onLoad() {
        plugin = this
    }

    override fun onEnable() {
        logger.info("코사인 라이브러리 활성화")

        adapters()

        server.pluginManager.registerEvents(InventoryListener(), this)
    }

    override fun onDisable() {
        logger.info("코사인 라이브러리 비활성화")
    }

    private fun adapters() {
        CommandManager.registerAdapter(
            ArgumentAdapter("닉네임",
                object: Castable<Player> {
                    override fun cast(string: String?): Player? = string?.run(server::getPlayer)
                }
            ),
/*            ArgumentAdapter("숫자",
                object: Castable<Int> {
                    override fun cast(string: String?): Int? = try { string?.run(Integer::parseInt) } catch (e: NumberFormatException) { null }
                }
            ) { emptyList() }*/
        )
    }
}