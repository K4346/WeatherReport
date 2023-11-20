package com.example.weatherreport.data.api.services

import com.example.weatherreport.data.entities.WeatherSummaryEntity
import retrofit2.Response
import retrofit2.http.*

//NOTE: решил не делать обертку для АПИ так как это приложение-пример
interface ApiService {
    @GET(value = "data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") order: String = "d2bb30c839618d9a6f2d0d995247af7d",
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric"
    ): Response<WeatherSummaryEntity>


}