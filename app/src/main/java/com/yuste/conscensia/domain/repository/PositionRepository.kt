package com.yuste.conscensia.domain.repository

import com.yuste.conscensia.domain.model.RelativePosition
import kotlinx.coroutines.flow.Flow

interface PositionRepository {

    fun observePosition(): Flow<RelativePosition>

    suspend fun getPosition(): RelativePosition

    suspend fun savePosition(position: RelativePosition)

}