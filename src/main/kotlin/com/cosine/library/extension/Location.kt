package com.cosine.library.extension

import org.bukkit.Location

fun Location.getFrontLocation(distance: Double, absolute: Boolean = true): Location {
    val vec = direction
    if (absolute) vec.setY(0)
    return clone().add(vec.normalize().multiply(distance))
}

fun Location.getAngleLocation(angle: Float, distance: Double): Location {
    return with(clone()) {
        yaw += angle
        add(direction.setY(0).normalize().multiply(distance))
        this
    }
}

fun Location.getLookTargetDiagonal(angle: Double, distance: Double): Location {
    return clone().also {
        it.yaw += angle.toFloat()
        it.add(it.direction.setY(0).normalize().multiply(distance))
        it.direction = this.toVector().clone().subtract(it.toVector())
    }
}