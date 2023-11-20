package com.example.weatherreport.domain.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import retrofit2.Response

interface WeatherInfoRepository {
    suspend fun getWeatherSummaryFromNet(
        lat: Double,
        lon: Double,
    ): Response<WeatherSummaryEntity>

    fun getWeatherSummaryFromDB(context: Context): LiveData<WeatherSummaryEntity?>

    fun insertWeatherSummaryToDB(context: Context, weatherSummaryEntity: WeatherSummaryEntity)
}