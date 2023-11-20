package com.example.weatherreport.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_summary")
data class WeatherSummaryEntity(
    @PrimaryKey
    val city: City,
    val list: List<WeatherEntity>,
)
