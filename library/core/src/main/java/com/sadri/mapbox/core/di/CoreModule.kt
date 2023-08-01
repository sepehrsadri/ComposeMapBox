package com.sadri.mapbox.core.di

import com.sadri.mapbox.core.dispatcher.DispatcherProvider
import com.sadri.mapbox.core.dispatcher.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
  @Provides
  @Singleton
  fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}