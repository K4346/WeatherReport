package com.example.weatherreport.ui.detailInfo.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import com.example.weatherreport.App
import com.example.weatherreport.R
import com.example.weatherreport.data.entities.WeatherEntity
import com.example.weatherreport.databinding.WeatherTimestampItemBinding
import com.example.weatherreport.domain.use_cases.DateUseCase
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ForecastTimestampsDaysAdapter(val resources: Resources) :
    RecyclerView.Adapter<ForecastTimestampsDaysAdapter.WeatherInfoViewHolder>() {
    var timestampsInfo: List<WeatherEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @Inject
    lateinit var dateUseCase: DateUseCase

    init {
        App().component.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        val binding =
            WeatherTimestampItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherInfoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val currentDay = timestampsInfo[position]
        with(holder) {
            if (currentDay.weather?.size != 0) {
                //                NOTE: Так как задание не требует, я решил всегда брать 1 картинку, даже если в промежуток времени было несколько разных состояний погоды, для описания я тоже решил не делать сложных оберток а засунуть в одну строку
                tvDescription.isVisible = true
                tvDescription.text = getDescription(currentDay)
                ivWeather.load(
                    resources.getString(
                        R.string.icon_url,
                        currentDay.weather?.get(0)?.icon
                    )
                )
                ivWeather.isVisible = true
            } else {
                tvDescription.isVisible = false
                tvDescription.text = ""
                ivWeather.dispose()
                ivWeather.isVisible = false
            }
            tvTimestamp.text = getTimestamp(currentDay)
            tvTemp.text = getTemp(currentDay)
            tvFeelsLike.text = getFeelsLike(currentDay)
            tvCloudiness.text = getCloudiness(currentDay)
            tvHumidity.text = getHumidity(currentDay)
            tvPressure.text = getPressure(currentDay)
            tvWind.text = getWind(currentDay)

            if (currentDay.rain == null) {
                tvRain.isVisible = false
                tvRain.text = ""
            } else {
                tvRain.isVisible = true
                tvRain.text = getRain(currentDay)
            }

            if (currentDay.snow == null) {
                tvSnow.isVisible = false
                tvSnow.text = ""
            } else {
                tvSnow.isVisible = true
                tvSnow.text = getSnow(currentDay)
            }
        }

    }

    private fun getTimestamp(timestamp: WeatherEntity): String {
        val date = dateUseCase.getLocalDate(timestamp.unixDateTime)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun getDescription(timestamp: WeatherEntity): String {
        return timestamp.weather?.joinToString(" / ") { weather ->
            weather.description
        } ?: ""
    }


    private fun getTemp(timestamp: WeatherEntity): String {
        return resources.getString(R.string.temp_value, timestamp.main.temp.toString())
    }

    private fun getFeelsLike(timestamp: WeatherEntity): String {
        return resources.getString(
            R.string.feels_like_temp_value,
            timestamp.main.feels_like.toString()
        )
    }

    private fun getCloudiness(timestamp: WeatherEntity): String {
        return resources.getString(
            R.string.cloudiness_value,
            timestamp.clouds.all.toString()
        )
    }

    private fun getHumidity(timestamp: WeatherEntity): String {
        return resources.getString(R.string.humidity, timestamp.main.humidity.toString())
    }

    private fun getPressure(timestamp: WeatherEntity): String {
        return resources.getString(
            R.string.pressure_hpa_value,
            timestamp.main.pressure.toString()
        )
    }

    private fun getWind(timestamp: WeatherEntity): String {
        return resources.getString(R.string.wind_ms_value, timestamp.wind.speed.toString())
    }

    private fun getRain(timestamp: WeatherEntity): String {
        return resources.getString(R.string.rainfall_value, timestamp.rain?.indicator.toString())
    }

    private fun getSnow(timestamp: WeatherEntity): String {
        return resources.getString(R.string.snowfall_value, timestamp.snow?.indicator.toString())
    }

    override fun getItemCount() = timestampsInfo.size
    inner class WeatherInfoViewHolder(itemView: WeatherTimestampItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvTimestamp = itemView.tvTimestamp
        val ivWeather = itemView.ivWeather
        val tvDescription = itemView.tvDescription
        val tvTemp = itemView.tvTemp
        val tvFeelsLike = itemView.tvFeelsLike
        val tvCloudiness = itemView.tvClouds
        val tvHumidity = itemView.tvHumidity
        val tvPressure = itemView.tvPressure
        val tvWind = itemView.tvWindSpeed
        val tvRain = itemView.tvRain
        val tvSnow = itemView.tvSnow
    }

}