package com.example.weatherreport.ui.home.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.R
import com.example.weatherreport.data.entities.WeatherEntity
import com.example.weatherreport.databinding.WeatherItemBinding
import com.example.weatherreport.domain.entities.ForecastDayEntity
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastDaysAdapter(val resources: Resources) :
    RecyclerView.Adapter<ForecastDaysAdapter.WeatherInfoViewHolder>() {
    var daysInfoList: List<ForecastDayEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onClickListener: OnWeatherDayClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        val binding =
            WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WeatherInfoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        val currentDay = daysInfoList[position]
        with(holder) {

            tvDate.text = getDate(currentDay)
            tvTemp.text = getTemp(currentDay.forecastTimestamps)
            tvCloudiness.text = getCloudiness(currentDay.forecastTimestamps)
            tvHumidity.text = getHumidity(currentDay.forecastTimestamps)
            tvPressure.text = getPressure(currentDay.forecastTimestamps)
            tvWind.text = getWind(currentDay.forecastTimestamps)
        }
        holder.itemView.setOnClickListener {
            onClickListener?.OnItemClick(currentDay)
        }
    }

    private fun getDate(day: ForecastDayEntity): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(day.localDate)
    }

    private fun getTemp(day: List<WeatherEntity>): String {
        return resources.getString(
            R.string.from_c_to_c,
            day.minOf { it.main.temp_min }.toString(),
            day.minOf { it.main.temp_max }.toString()
        )
    }

    private fun getCloudiness(day: List<WeatherEntity>): String {
        return resources.getString(
            R.string.cloudiness_value,
            (day.minOf { it.clouds.all }).toString()
        )
    }

    private fun getHumidity(day: List<WeatherEntity>): String {
        return resources.getString(R.string.humidity, day.minOf { it.main.humidity }.toString())
    }

    private fun getPressure(day: List<WeatherEntity>): String {
        return resources.getString(
            R.string.pressure_hpa_value,
            day.minOf { it.main.pressure }.toString()
        )
    }

    private fun getWind(day: List<WeatherEntity>): String {
        return resources.getString(R.string.wind_ms_value, day.minOf { it.wind.speed }.toString())
    }

    override fun getItemCount() = daysInfoList.size
    inner class WeatherInfoViewHolder(itemView: WeatherItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val tvDate = itemView.tvDate
        val tvTemp = itemView.tvTemp
        val tvCloudiness = itemView.tvCloudiness
        val tvHumidity = itemView.tvHumidity
        val tvPressure = itemView.tvPressure
        val tvWind = itemView.tvWindSpeed
    }


    interface OnWeatherDayClickListener {
        fun OnItemClick(weatherDayInfo: ForecastDayEntity)
    }

}