package com.example.weatherreport.di

import com.example.weatherreport.domain.use_cases.DateUseCase
import com.example.weatherreport.domain.use_cases.WeatherUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideDateUseCase(): DateUseCase {
        return DateUseCase()
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(): WeatherUseCase {
        return WeatherUseCase()
    }
}