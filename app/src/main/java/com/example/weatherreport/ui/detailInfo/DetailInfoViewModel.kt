package com.example.weatherreport.ui.detailInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherreport.App
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import com.example.weatherreport.domain.repositories.WeatherInfoRepository
import com.example.weatherreport.domain.use_cases.WeatherUseCase
import javax.inject.Inject

class DetailInfoViewModel(app: Application) : AndroidViewModel(app) {

    var currentDate: Int = -1

    @Inject
    lateinit var weatherInfoRepository: WeatherInfoRepository

    @Inject
    lateinit var weatherUseCase: WeatherUseCase

    val weatherSummaryEntity: LiveData<WeatherSummaryEntity?>


    init {
        App().component.inject(this)
        weatherSummaryEntity = weatherInfoRepository.getWeatherSummaryFromDB(app)
    }

    fun getForecastTimestampList(weatherSummary: WeatherSummaryEntity, day: Int) =
        weatherUseCase.getTimestampsByDayFromSummary(weatherSummary, day)

}