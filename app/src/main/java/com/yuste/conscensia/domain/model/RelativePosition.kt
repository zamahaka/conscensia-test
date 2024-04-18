package com.yuste.conscensia.domain.model

import androidx.annotation.FloatRange

data class RelativePosition(
    @FloatRange(from = 0.0, to = 1.0) val x: Float,
    @FloatRange(from = 0.0, to = 1.0) val y: Float,
) {

    fun position(
        size: Size,
        bounds: Bounds,
    ): OffsetF {
        return OffsetF(
            x = bounds.width * x,
            y = bounds.height * y,
        )
            .coerceAtLeast(Bounds.ZERO)
            .coerceAtMost(bounds - size)
    }

}