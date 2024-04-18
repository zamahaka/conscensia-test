package com.yuste.conscensia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuste.conscensia.di.Dispatcher
import com.yuste.conscensia.domain.model.RelativePosition
import com.yuste.conscensia.domain.repository.PositionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DragAndDropViewModel @Inject constructor(
    private val repository: PositionRepository,
    @Dispatcher.Io private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val imagePosition = repository.observePosition().flowOn(ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<DragAndDropState> = imagePosition
        .mapLatest { position -> DragAndDropState(position) }
        .stateIn(viewModelScope, WhileSubscribed(5_000), initialState())

    private val _alerts = MutableSharedFlow<DragAndDropAlert>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = DROP_OLDEST,
    )
    val alerts: SharedFlow<DragAndDropAlert> = _alerts.asSharedFlow()


    fun repositionImage(position: RelativePosition) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                repository.savePosition(position)
            }

            _alerts.emit(DragAndDropAlert.RepositionSuccessful(position))
        }
    }


    private fun initialState(): DragAndDropState = DragAndDropState(RelativePosition(0f, 0f))

}
