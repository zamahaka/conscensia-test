package com.yuste.conscensia.domain.model

data class Bounds(
    val width: Int,
    val height: Int,
) {
    companion object {
        val ZERO = Bounds(0, 0)
    }
}

operator fun Bounds.minus(size: Size): Bounds {
    return Bounds(
        width = width - size.width,
        height = height - size.height,
    )
}
