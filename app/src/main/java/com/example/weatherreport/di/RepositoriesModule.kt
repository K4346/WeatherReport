package com.example.weatherreport.di

import com.example.weatherreport.data.repositories.WeatherInfoRepositoryImpl
import com.example.weatherreport.domain.repositories.WeatherInfoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    fun provideWeatherInfoRepository(): WeatherInfoRepository {
        return WeatherInfoRepositoryImpl()
    }
}
