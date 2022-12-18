package com.cosine.library.item

import com.cosine.library.extension.getOfflinePlayer
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.UUID

class ItemBuilder(
    material: Material = Material.SKULL_ITEM,
    durability: Short = 0,
    amount: Int = 1
) {
    private var itemStack: ItemStack
    private var itemMeta: ItemMeta

    init {
        itemStack = ItemStack(material, amount, if (material == Material.SKULL_ITEM) 3 else durability)
        itemMeta = itemStack.itemMeta
    }

    fun setDisplayName(displayName: String): ItemBuilder {
        itemMeta.displayName = displayName
        return this
    }

    fun setLore(lore: List<String>): ItemBuilder {
        itemMeta.lore = lore
        return this
    }

    fun setGlow(): ItemBuilder {
        itemStack.itemMeta = itemMeta
        itemStack.addUnsafeEnchantment(Enchantment.LURE, 1)
        itemMeta = itemStack.itemMeta
        addItemFlags(ItemFlag.HIDE_ENCHANTS)
        return this
    }

    fun addItemFlags(vararg flags: ItemFlag): ItemBuilder {
        itemMeta.addItemFlags(*flags)
        return this
    }

    fun setUnbreakable(): ItemBuilder {
        itemMeta.isUnbreakable = true
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }
}
fun ItemStack.setSkull(uuid: UUID) {
    getOfflinePlayer(uuid) {
        val meta = this.itemMeta as SkullMeta
        meta.owningPlayer = it
        this.itemMeta = meta
    }
}