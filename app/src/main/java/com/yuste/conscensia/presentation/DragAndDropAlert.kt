package com.yuste.conscensia.presentation

import com.yuste.conscensia.domain.model.RelativePosition

sealed interface DragAndDropAlert {

    data class RepositionSuccessful(
        val position: RelativePosition,
    ) : DragAndDropAlert

}