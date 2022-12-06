package com.cosine.library.extension

import com.cosine.library.locale.LocaleQuery
import com.google.gson.Gson
import net.minecraft.server.v1_12_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

inline fun <reified T> ItemStack.addNBTTag(obj: T) {
    itemMeta = CraftItemStack.asBukkitCopy(
        CraftItemStack.asNMSCopy(this).apply {
            tag = (tag?: NBTTagCompound())
                .apply {
                    setString(T::class.simpleName, Gson().toJson(obj))
                }
        }
    ).itemMeta
}

inline fun <reified T> ItemStack.getNBTTag(clazz: Class<T>): T? {
    val jsonData = CraftItemStack.asNMSCopy(this).tag?.getString(T::class.simpleName)?: return null
    return Gson().fromJson(jsonData, clazz)
}

inline fun <reified T> ItemStack.hasNBTTag(clazz: Class<T>): Boolean {
    return CraftItemStack.asNMSCopy(this).tag?.hasKey(T::class.java.simpleName)?: false
}

inline fun <reified T> ItemStack.removeNBTTag(clazz: Class<T>) {
    itemMeta = CraftItemStack.asBukkitCopy(
        CraftItemStack.asNMSCopy(this).apply {
            tag?.remove(T::class.java.simpleName)
        }
    ).itemMeta
}

val ItemStack.korean: String
    get() =
        if(hasItemMeta() && itemMeta.hasDisplayName()) itemMeta.displayName
        else LocaleQuery.getItemKey(type, durability, "?")