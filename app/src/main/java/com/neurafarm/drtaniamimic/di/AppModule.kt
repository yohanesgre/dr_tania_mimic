package com.neurafarm.drtaniamimic.di

import android.content.Context
import com.neurafarm.drtaniamimic.data.sharedpreferences.AppSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideAppSharedPreferences(@ApplicationContext appContext: Context): AppSharedPreferencesImpl =
        AppSharedPreferencesImpl(appContext)
}