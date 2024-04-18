package com.yuste.conscensia.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.yuste.conscensia.domain.model.RelativePosition
import com.yuste.conscensia.domain.repository.PositionRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@ViewModelScoped
class PreferencesPositionRepository @Inject constructor(
    private val preferences: SharedPreferences,
) : PositionRepository {

    override fun observePosition(): Flow<RelativePosition> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key != X_KEY && key != Y_KEY) return@OnSharedPreferenceChangeListener

            runBlocking { trySend(getPosition()) }
        }

        trySend(getPosition())
        preferences.registerOnSharedPreferenceChangeListener(listener)
        awaitClose {
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    override suspend fun getPosition(): RelativePosition {
        return RelativePosition(
            x = preferences.getFloat(X_KEY, 0f),
            y = preferences.getFloat(Y_KEY, 0f),
        )
    }

    override suspend fun savePosition(position: RelativePosition) {
        preferences.edit {
            putFloat(X_KEY, position.x)
            putFloat(Y_KEY, position.y)
        }
    }


    private companion object {
        const val X_KEY = "position.x"
        const val Y_KEY = "position.y"
    }

}