package com.example.weatherreport.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherreport.App
import com.example.weatherreport.data.api.services.ApiService
import com.example.weatherreport.data.database.AppDatabase
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import com.example.weatherreport.domain.repositories.WeatherInfoRepository
import retrofit2.Response
import javax.inject.Inject

class WeatherInfoRepositoryImpl : WeatherInfoRepository {
    @Inject
    lateinit var apiService: ApiService


    val app = App()

    //    NOTE: Решил не дробить на разные репозитории работу с интернетом/бд из-за слишком маленького функционала
    override suspend fun getWeatherSummaryFromNet(
        lat: Double,
        lon: Double
    ): Response<WeatherSummaryEntity> {

        app.component.inject(this)
        return apiService.getWeatherForecast(lat, lon)
    }

    override fun getWeatherSummaryFromDB(context: Context): LiveData<WeatherSummaryEntity?> {
        app.component.inject(this)
        return AppDatabase.getInstance(context).weatherDao().getWeatherSummary()
    }

    override fun insertWeatherSummaryToDB(
        context: Context,
        weatherSummaryEntity: WeatherSummaryEntity
    ) {
        app.component.inject(this)
        val dao = AppDatabase.getInstance(context).weatherDao()
        dao.insert(weatherSummaryEntity)
    }
}