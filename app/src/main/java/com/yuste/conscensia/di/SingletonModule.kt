package com.yuste.conscensia.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {

    companion object {

        @Provides
        @Dispatcher.Io
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @Singleton
        fun providesStandardSharedPreferences(
            @ApplicationContext context: Context,
        ): SharedPreferences = context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)

    }

}