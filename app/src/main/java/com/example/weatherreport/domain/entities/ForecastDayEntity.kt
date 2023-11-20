package com.example.weatherreport.domain.entities

import com.example.weatherreport.data.entities.City
import com.example.weatherreport.data.entities.WeatherEntity
import java.util.Date

data class ForecastDayEntity(
    val forecastTimestamps: List<WeatherEntity>,
    val localDate: Date,
)

data class ForecastEntity(
    val city: City,
    val forecastDayEntity: List<ForecastDayEntity>
)
