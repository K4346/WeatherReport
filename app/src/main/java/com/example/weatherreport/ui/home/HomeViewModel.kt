package com.example.weatherreport.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.App
import com.example.weatherreport.R
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import com.example.weatherreport.domain.repositories.WeatherInfoRepository
import com.example.weatherreport.domain.use_cases.DateUseCase
import com.example.weatherreport.domain.use_cases.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class HomeViewModel(val app: Application) : AndroidViewModel(app) {


    @Inject
    lateinit var weatherInfoRepository: WeatherInfoRepository

    @Inject
    lateinit var dateUseCase: DateUseCase

    @Inject
    lateinit var weatherUseCase: WeatherUseCase


    val weatherSummaryEntity: LiveData<WeatherSummaryEntity?>
    val errorMLD = MutableLiveData<String>()

    init {
        App().component.inject(this)
        weatherSummaryEntity = weatherInfoRepository.getWeatherSummaryFromDB(app)
    }

    fun getWeatherSummary(latitude: Double, longitude: Double) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherSummaryBodyRequest =
                    weatherInfoRepository.getWeatherSummaryFromNet(latitude, longitude)
                weatherSummaryBodyRequest.body()?.let {
                    errorMLD.postValue("")
                    insertWeatherSummaryToDB(it)
                }
            } catch (e: Exception) {
//                Так как нам не важно проблемы с сервером или отсутсвие интернета (итог один) я решил не делать красивых оберток а обработать в try catch
                errorMLD.postValue(app.getString(R.string.no_internet_connection))
            }

        }
    }

    private fun insertWeatherSummaryToDB(weatherSummary: WeatherSummaryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.insertWeatherSummaryToDB(app, weatherSummary)
        }
    }

    fun getForecastDaysList(weatherSummary: WeatherSummaryEntity) =
        weatherUseCase.getForecastOfDays(weatherSummary)

    fun getCurrentDay(date: Date): Int = dateUseCase.getDayFromDate(date)


}