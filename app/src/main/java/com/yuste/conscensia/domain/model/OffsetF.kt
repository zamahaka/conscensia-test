package com.yuste.conscensia.domain.model

data class OffsetF(
    val x: Float,
    val y: Float,
)

fun OffsetF.coerceAtLeast(bounds: Bounds): OffsetF {
    return OffsetF(
        x = x.coerceAtLeast(bounds.width.toFloat()),
        y = y.coerceAtLeast(bounds.height.toFloat()),
    )
}

fun OffsetF.coerceAtMost(bounds: Bounds): OffsetF {
    return OffsetF(
        x = x.coerceAtMost(bounds.width.toFloat()),
        y = y.coerceAtMost(bounds.height.toFloat()),
    )
}