package com.example.weatherreport.di

import com.example.weatherreport.data.repositories.WeatherInfoRepositoryImpl
import com.example.weatherreport.domain.use_cases.WeatherUseCase
import com.example.weatherreport.ui.detailInfo.DetailInfoViewModel
import com.example.weatherreport.ui.detailInfo.adapters.ForecastTimestampsDaysAdapter
import com.example.weatherreport.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiFactoryModule::class, ApiServiceModule::class, RepositoriesModule::class, UseCasesModule::class])
interface AppComponent {

    fun inject(weatherInfoRepository: WeatherInfoRepositoryImpl)
    fun inject(adapter: ForecastTimestampsDaysAdapter)
    fun inject(weatherUseCase: WeatherUseCase)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: DetailInfoViewModel)

}