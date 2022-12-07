package com.cosine.library.command

import com.cosine.library.event.command.CosineAttemptCommandEvent
import com.cosine.library.event.command.CosineCompleteCommandEvent
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.StringUtil
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.jvmErasure

abstract class CosineCommand(
    command: String,
    private val plugin: JavaPlugin,
    val prefix: String = "§c§l[ CosineLibrary ]§f",
): CommandExecutor, TabCompleter {

    companion object {
        private const val NULLABLE_BOX = "[%s]"
        private const val NONNULL_BOX = "<%s>"
    }

    private val command: PluginCommand
    init { this.command = plugin.getCommand(command) }

    private val arguments = HashMap<String, CommandArgument>()

    inner class CommandArgument(
        argument: String,
        val annotation: CosineSubCommand,
        private val function: KFunction<Unit>
    ) {

        // second - nonnull (true)
        private val params = ArrayList<Pair<ArgumentAdapter<*>, Boolean>>()
        private var description = "$prefix /${command.name} $argument"

        init {
            function.parameters.forEachIndexed { index, param ->
                if (index in 0 .. 1) {
                    return@forEachIndexed
                }

                val name = param.type.jvmErasure.simpleName
                    .run { if (this == "Int") "Integer" else this }
                val arg = ArgumentAdapter.getArgument(name)
                    ?.apply { params.add(this to !param.type.isMarkedNullable) }

                description +=
                    if (param.type.isMarkedNullable) {
                        " ${String.format(NULLABLE_BOX, arg?.label?:"에러")}"
                    } else {
                        " ${String.format(NONNULL_BOX, arg?.label?:"에러")}"
                    }
            }
            description += " : ${annotation.description}"
        }

        fun printDescription(receiver: Player) = receiver.sendMessage(description)

        fun getArgument(player: Player, index: Int): ArgumentAdapter<*>? {
            return if (!player.isOp && annotation.isOp) {
                null
            } else try {
                params[index].first
            } catch (e: IndexOutOfBoundsException) {
                null
            }
        }

        fun runArgument(player: Player, args: Array<String>) {
            if (annotation.isOp  && !player.isOp) {
                player.sendMessage("$prefix §f권한이 없습니다.")
                return
            }

            val arguments = ArrayList<Any?>()
            try {
                params.forEachIndexed { index, pair ->
                    val param =
                        try {
                            pair.first.cast(args[index])
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        }
                    if (pair.second && param == null) {
                        player.sendMessage("$prefix §f${pair.first.label}(을)를 입력해주세요.")
                    } else {
                        arguments.add(param)
                    }
                }
                function.javaMethod?.invoke(this@CosineCommand, player, *arguments.toTypedArray())
            } catch (_:Exception) {}
        }
    }

    init {
        val functions = this::class.memberFunctions.filterIsInstance<KFunction<Unit>>()
        functions.forEach {
            it.annotations.filterIsInstance<CosineSubCommand>().forEach { anno ->
                arguments[anno.argument] = CommandArgument(anno.argument, anno, it)
            }
        }
    }

    fun register() {
        command.executor = this
        command.tabCompleter = this
    }

    private fun printHelp(player: Player) {
        player.sendMessage("$prefix ${command.name} - ${command.description.run { ifEmpty { "도움말" } }}")
        arguments.values.forEach {
            if (it.annotation.isOp && !player.isOp) return@forEach
            it.printDescription(player)
        }
    }

    open fun runDefaultCommand(player: Player) = printHelp(player)

    private fun onCommand(player: Player, args: Array<String>) {
        val commandData = command.run {
            if (args.isEmpty()) {
                CosineCommandData(name, null, "/${name}", description)
            } else {
                val subCommands = args.copyOfRange(0, args.size).joinToString(" ")
                CosineCommandData(name, args[0], "/${name} $subCommands", description)
            }
        }
        val manager = plugin.server.pluginManager
        val attempt = CosineAttemptCommandEvent(player, commandData)
        manager.callEvent(attempt)
        if (attempt.isCancelled) return
        try {
            if (!commandData.isSubCommand) {
                runDefaultCommand(player)
            } else {
                arguments[args[0]]!!.runArgument(player, args.copyOfRange(1, args.size))
            }
            manager.callEvent(CosineCompleteCommandEvent(player, commandData))
        } catch (_: Exception) {}
    }

    open fun tabComplete(player: Player, args: Array<String>): List<String>? {
        return when(args.size) {
            in 0..1 -> {
                val tabList = arguments.filter { (_, v) ->
                    !(v.annotation.isOp && !player.isOp)
                }.keys.toList()
                StringUtil.copyPartialMatches(args[0], tabList, ArrayList())
            }
            else -> {
                val index = args.size - 2
                val target = arguments[args[0]]
                if (target == null) null
                else {
                    val tab = target.getArgument(player, index)
                    if (tab == null) {
                        emptyList()
                    } else {
                        tab.tabCompleter.run {
                            invoke()?.run {
                                StringUtil.copyPartialMatches(args[args.size - 1], this, ArrayList())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) onCommand(sender, args)
        return false
    }

    override fun onTabComplete(sender: CommandSender, cmd: Command, label: String, args: Array<String>): List<String>? {
        return if (sender is Player) tabComplete(sender, args) else null
    }

}