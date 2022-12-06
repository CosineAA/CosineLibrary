package com.cosine.library.packet

import net.minecraft.server.v1_12_R1.*
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.entity.Player

class PacketArmorStand(
    private val location: Location,
    private var text: String,
    private val viewers: List<Player>,
    private var item: org.bukkit.inventory.ItemStack? = null,
) {

    private val armorStand = EntityArmorStand((location.world as CraftWorld).handle, location.x, location.y, location.z)
    private val spawnPacket: PacketPlayOutSpawnEntityLiving
    private val destroyPacket: PacketPlayOutEntityDestroy
    private val armorStandId: Int
    private val handles by lazy { viewers.map { (it as CraftPlayer).handle.playerConnection }}

    fun show() {
        handles.forEach { it.sendPacket(spawnPacket) }
        item?.apply(::setItem)
    }
    fun hide() = handles.forEach { it.sendPacket(destroyPacket) }

    fun setText(text: String) {
        this.text = text
        armorStand.customName = text
        if(text.isEmpty()) armorStand.customNameVisible = false
        handles.forEach { it.sendPacket(PacketPlayOutEntityMetadata(armorStandId, armorStand.dataWatcher, true)) }
    }

    fun teleport(location: Location) {
        armorStand.setLocation(location.x, location.y ,location.z, location.yaw, location.pitch)
        handles.forEach { it.sendPacket(PacketPlayOutEntityTeleport(armorStand)) }
    }

    fun setItem(item: org.bukkit.inventory.ItemStack) {
        this.item = item
        handles.forEach {
            it.sendPacket(PacketPlayOutEntityEquipment(
                armorStandId,
                EnumItemSlot.HEAD,
                CraftItemStack.asNMSCopy(item)
            ))
            it.sendPacket(PacketPlayOutEntityMetadata(armorStandId, armorStand.dataWatcher, true))
        }
    }

    init {
        with(armorStand) {
            isInvisible = true
            customName = text
            customNameVisible = text.isNotEmpty()
            isSmall = true
            isMarker = item == null
            armorStandId = id
            setLocation(location.x, location.y, location.z, location.yaw, location.pitch)
        }

        spawnPacket = PacketPlayOutSpawnEntityLiving(armorStand)
        destroyPacket = PacketPlayOutEntityDestroy(armorStandId)
    }

}