package com.cosine.library

import com.cosine.library.command.Argument
import com.cosine.library.command.CosineCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ExampleCommand(plugin: JavaPlugin): CosineCommand("테스트", plugin, "§6§l[ prefix ]§f") {

    // /테스트 만 쳤을 때 실행됨
    /*override fun runDefaultCommand(sender: Player) {
        // 없을 시 기본 값(help) 출력
    }*/

    // /테스트 텔레포트 <타겟>
    @Argument("텔레포트", "텔포함")
    fun teleportCommand(player: Player, target: Player) {
        player.sendMessage(target.name)
    }
}