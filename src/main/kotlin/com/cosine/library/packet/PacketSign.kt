package com.cosine.library.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.BlockPosition
import com.comphenix.protocol.wrappers.nbt.NbtCompound
import com.cosine.library.CosineLibrary.Companion.plugin
import com.cosine.library.extension.later
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.stream.IntStream

class PacketSign(
    val player: Player,
    val textLine: Array<String>,
    val response: (Array<String>)->Boolean
) {

    private val location = player.location
    private val position = BlockPosition(location.blockX, 0, location.blockZ)
    private var originalBlock = Material.AIR

    companion object {
        private val receivers = HashMap<Player, PacketSign>()
        fun listener() {
            ProtocolLibrary.getProtocolManager()
                .addPacketListener(object: PacketAdapter(plugin, PacketType.Play.Client.UPDATE_SIGN) {
                    override fun onPacketReceiving(event: PacketEvent) {
                        val player = event.player
                        val sign = receivers.remove(player) ?: return

                        event.isCancelled = true

                        if (sign.response(event.packet.stringArrays.read(0)))
                            player.sendBlockChange(sign.position.toLocation(player.world), sign.originalBlock, 0)
                        else
                            later(1) { sign.open() }
                    }
                })
        }
    }

    fun open() {
        position.toLocation(location.world).apply {
            originalBlock = block.type
            player.sendBlockChange(this, Material.SIGN_POST, 0)
        }
        val manager = ProtocolLibrary.getProtocolManager()
        val signEditorPacket = manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR)
        val signDataPacket = manager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA)

        signEditorPacket.blockPositionModifier.write(0, position)

        val signNBT = signDataPacket.nbtModifier.read(0) as NbtCompound

        signNBT.apply {
            IntStream.range(0, 4).forEach { i ->
                val text = "{\"text\":\"${textLine[i]}\"}"
                put("Text${i + 1}", text)
            }
        }

        signDataPacket.blockPositionModifier.write(0, position)
        signDataPacket.integers.write(0, 9)
        signDataPacket.nbtModifier.write(0, signNBT)

        manager.apply {
            sendServerPacket(player, signDataPacket)
            sendServerPacket(player, signEditorPacket)
            receivers[player] = this@PacketSign
        }
    }

}