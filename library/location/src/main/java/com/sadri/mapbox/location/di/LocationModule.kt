package com.sadri.mapbox.location.di

import com.sadri.mapbox.location.data.repository.LocationRepository
import com.sadri.mapbox.location.data.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
  @Provides
  @Singleton
  fun bindLocationRepository(
    locationRepositoryImpl: LocationRepositoryImpl
  ): LocationRepository = locationRepositoryImpl
}