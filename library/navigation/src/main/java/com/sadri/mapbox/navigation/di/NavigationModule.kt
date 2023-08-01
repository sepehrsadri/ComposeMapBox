package com.sadri.mapbox.navigation.di

import com.sadri.mapbox.navigation.domain.NavigationProvider
import com.sadri.mapbox.navigation.domain.NavigationProviderImpl
import com.sadri.mapbox.navigation.data.repository.NavigationRepository
import com.sadri.mapbox.navigation.data.repository.NavigationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
  @Provides
  @Singleton
  fun bindNavigationProvider(
    navigationProviderImpl: NavigationProviderImpl
  ): NavigationProvider = navigationProviderImpl

  @Provides
  @Singleton
  fun bindNavigationRepository(
    navigationRepositoryImpl: NavigationRepositoryImpl
  ): NavigationRepository = navigationRepositoryImpl
}