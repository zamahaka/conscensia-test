package com.yuste.conscensia.presentation

import android.content.Context
import com.yuste.conscensia.R
import com.yuste.conscensia.presentation.DragAndDropAlert.RepositionSuccessful
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MapDragAndDropAlertToString @Inject constructor(
    @ActivityContext private val context: Context,
) : (DragAndDropAlert) -> String {

    override fun invoke(alert: DragAndDropAlert): String {
        return when (alert) {
            is RepositionSuccessful -> context.getString(
                R.string.alertMessage_reposition_success,
                alert.position.x,
                alert.position.y,
            )
        }
    }
}
