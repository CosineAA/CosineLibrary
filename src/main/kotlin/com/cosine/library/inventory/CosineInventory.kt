package com.cosine.library.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.lang.Exception

abstract class CosineInventory(
    private val display: String? = null,
    private val row: Int = 1,
    private val isCancelled: Boolean = false,
    private val inventoryType: InventoryType = InventoryType.CHEST,
): InventoryHolder {

    override fun getInventory(): Inventory {
        val inventory =
            if(inventoryType == InventoryType.CHEST) Bukkit.createInventory(this, row * 9, display)
            else Bukkit.createInventory(this, inventoryType, display)

        init(inventory)

        return inventory
    }

    abstract fun init(inventory: Inventory)

    fun openInventory(player: Player) {
        player.openInventory(inventory)
    }

    internal fun onInventoryClick(event: InventoryClickEvent) {
        if(isCancelled) event.isCancelled = true
        onClick(event)
    }

    internal fun onInventoryClose(event: InventoryCloseEvent) {
        onClose(event)
    }

    protected open fun onClick(event: InventoryClickEvent) {}
    protected open fun onClose(event: InventoryCloseEvent) {}

}