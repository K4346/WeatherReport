package com.example.weatherreport.domain.use_cases

import com.example.weatherreport.App
import com.example.weatherreport.data.entities.WeatherEntity
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import com.example.weatherreport.domain.entities.ForecastDayEntity
import com.example.weatherreport.domain.entities.ForecastEntity
import java.util.Date
import javax.inject.Inject

class WeatherUseCase {
    @Inject
    lateinit var dateUseCase: DateUseCase

    init {
        App().component.inject(this)
    }

    fun getForecastOfDays(weatherSummaryEntity: WeatherSummaryEntity): ForecastEntity {
//     NOTE:   данные не должны приходить с ошибкой, поэтому допустимо
//     NOTE:   важно, данные за день это данные за будущие периоды а не прошедшие
        var lastDay = -1

        val forecastByDays = mutableListOf<ForecastDayEntity>()
        var dayForecast = mutableListOf<WeatherEntity>()
        var date = Date()
// NOTE: замечу что с сервера приходит поправка на локальное время, но на всякий случай я возьму из телефона
        weatherSummaryEntity.list.forEach { timestamp ->

            date = dateUseCase.getLocalDate(timestamp.unixDateTime)
            val day = dateUseCase.getDayFromDate(date)

            if (lastDay != day && lastDay != -1) {
                // NOTE: Так как мы перешли на следующий день следует отнять 1 день

                val yesterday = dateUseCase.subtractOneDayFromDate(date)
                forecastByDays.add(
                    ForecastDayEntity(
                        dayForecast,
                        localDate = yesterday
                    )
                )
                dayForecast = mutableListOf()
            }
            dayForecast.add(timestamp)
            lastDay = day
        }
        forecastByDays.add(ForecastDayEntity(dayForecast, date))
//       NOTE: по требованию нужно только 5 дней, поэтому (0,5)
        val forecastWeather =
            ForecastEntity(weatherSummaryEntity.city, forecastByDays.subList(0, 5))
        return forecastWeather
    }

    fun getTimestampsByDayFromSummary(
        weatherSummaryEntity: WeatherSummaryEntity,
        rightDay: Int
    ): List<WeatherEntity> {
        val dayForecast = mutableListOf<WeatherEntity>()
        weatherSummaryEntity.list.forEach { timestamp ->

            val date = dateUseCase.getLocalDate(timestamp.unixDateTime)
            val day = dateUseCase.getDayFromDate(date)

            if (rightDay == day) {
                dayForecast.add(timestamp)
            }

        }

        return dayForecast
    }
}