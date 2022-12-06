package com.cosine.library.extension

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.isEmpty() =
    contents.none { it != null && it.type != Material.AIR }

fun Inventory.emptySlots(): Collection<Int> =
    List(contents.filter { it == null || it.type == Material.AIR }.size) { index -> index }

fun Inventory.addable(item: ItemStack): Boolean = contents.any {
    it == null || it.type == Material.AIR || ( it.isSimilar(item) && it.amount + item.amount <= it.maxStackSize )
}