package com.hometest.weather.di

import com.hometest.weather.data.repository.WeatherRepositoryImpl
import com.hometest.weather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsWeatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
