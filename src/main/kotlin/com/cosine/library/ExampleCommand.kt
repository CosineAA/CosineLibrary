package com.cosine.library

import com.cosine.library.command.CosineCommand
import org.bukkit.plugin.java.JavaPlugin

class ExampleCommand(plugin: JavaPlugin): CosineCommand("테스트", plugin, "§6§l[ prefix ]§f") {

    // onEnable
/*    CommandManager.registerAdapter(
    ArgumentAdapter("창고번호", object: Castable<ChestNumber> {
        override fun cast(string: String?): ChestNumber? {
            return string?.run { if(isInt()) toInt() else null }?.run { ChestNumber(this) }
        }
    }) { listOf("1", "2") }
    )*/

    // 파라미터 커스텀 이름
/*    data class ChestNumber(var number: Int) {
        fun asInt(): Int = number
        override fun toString(): String {
            return number.toString()
        }
    }*/

    // /테스트 만 쳤을 때 실행됨
    /*override fun runDefaultCommand(sender: Player) {
        // 없을 시 기본 값(help) 출력
    }*/

    // Command
/*    @CosineSubCommand("텔레포트", "텔포함")
    fun teleportCommand(player: Player, target: Player) {
        player.sendMessage(target.name)
    }*/
}