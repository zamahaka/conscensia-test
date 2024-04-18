package com.yuste.conscensia.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yuste.conscensia.R
import com.yuste.conscensia.databinding.FragmentDragAndDropBinding
import com.yuste.conscensia.domain.model.Bounds
import com.yuste.conscensia.domain.model.RelativePosition
import com.yuste.conscensia.domain.model.Size
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DragAndDropFragment : Fragment(R.layout.fragment_drag_and_drop) {

    @Inject
    internal lateinit var mapAlertToMessage: MapDragAndDropAlertToString

    private val viewModel by viewModels<DragAndDropViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentDragAndDropBinding.bind(view).setUpUi()
    }


    private fun FragmentDragAndDropBinding.setUpUi() {
        Glide.with(this@DragAndDropFragment)
            .load(IMAGE_URL)
            .into(dragChild)

        dragChild.setOnLongClickListener(StartDragAndDropLongClickListener())

        dragParent.setOnDragListener(
            RelativePositionDropOnDragListener onDrop@{ position ->
                // Update view position right-away, to avoid possible frames delays
                updateDragChildPosition(position)
                viewModel.repositionImage(position)
            }
        )

        launchCoroutineForUiState {
            launch { observeAlerts(viewModel.alerts) }
            launch { observeState(viewModel.state) }
        }
    }

    private suspend fun FragmentDragAndDropBinding.observeAlerts(
        flow: Flow<DragAndDropAlert>,
    ) = flow.collectLatest { showAlert(it) }

    private fun FragmentDragAndDropBinding.showAlert(
        alert: DragAndDropAlert,
    ) = showSnackbar(message = mapAlertToMessage(alert))


    private suspend fun FragmentDragAndDropBinding.observeState(
        flow: Flow<DragAndDropState>,
    ) = flow.collectLatest { bindState(it) }

    private fun FragmentDragAndDropBinding.bindState(
        state: DragAndDropState,
    ) {
        updateDragChildPosition(state.imagePosition)
    }


    private fun FragmentDragAndDropBinding.updateDragChildPosition(
        position: RelativePosition,
    ) {
        dragParent.doOnLayout {
            val offset = position.position(
                Size(dragChild.width, dragChild.height),
                Bounds(dragParent.width, dragParent.height),
            )

            dragChild.translationX = offset.x
            dragChild.translationY = offset.y
        }
    }

    private fun FragmentDragAndDropBinding.showSnackbar(
        message: String,
    ) = Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()


    private companion object {
        const val IMAGE_URL = "https://th.bing.com/th/id/OIG4.LgUj9FIjzUbdTSMn0mRg"
    }

}