package com.yuste.conscensia.di

import com.yuste.conscensia.data.PreferencesPositionRepository
import com.yuste.conscensia.domain.repository.PositionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindPositionRepository(
        impl: PreferencesPositionRepository,
    ): PositionRepository

}