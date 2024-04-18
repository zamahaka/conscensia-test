package com.yuste.conscensia.presentation

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.View
import androidx.core.view.isInvisible
import com.yuste.conscensia.domain.model.RelativePosition

/**
 * Initiates drag and drop that [RelativePositionDropOnDragListener] can respond to
 * */
class StartDragAndDropLongClickListener : View.OnLongClickListener {

    override fun onLongClick(v: View): Boolean {
        val item = ClipData.Item("view_dragged_item")

        val dragData = ClipData(
            v.tag as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item,
        )
        val shadow = View.DragShadowBuilder(v)

        v.startDragAndDrop(dragData, shadow, /* myLocalState = */ v, /* flags = */ 0)

        return true
    }

}

/**
 * Responds to drag and drop events initiated by [StartDragAndDropLongClickListener]
 * */
class RelativePositionDropOnDragListener(
    private val onDrop: (RelativePosition) -> Unit,
) : View.OnDragListener {

    override fun onDrag(dragParent: View, event: DragEvent): Boolean {
        val localState = event.localState

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (localState is View) {
                    localState.isInvisible = true
                    return true
                } else {
                    return false
                }
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                (localState as View).isInvisible = false

                return true
            }


            DragEvent.ACTION_DROP -> {
                val dragChild = (localState as View)
                if (!dragParent.isLaidOut) return false
                if (!dragChild.isLaidOut) return false

                val desiredAbsoluteX = event.x - dragChild.width / 2
                val desiredAbsoluteY = event.y - dragChild.height / 2

                val relativePosition = RelativePosition(
                    x = desiredAbsoluteX / dragParent.width,
                    y = desiredAbsoluteY / dragParent.height,
                )

                onDrop(relativePosition)
                return true
            }

            else -> return false
        }
    }

}