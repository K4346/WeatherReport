package com.example.weatherreport.data.entities

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    val clouds: Clouds,
    @SerializedName("dt")
    var unixDateTime: Long,
    val main: Main,
    val rain: Rain?,
    val snow: Snow?,
    val weather: List<Weather>?,
    val wind: Wind
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Clouds(
    val all: Int
)

data class Rain(
    @SerializedName("3h")
    val indicator: Double
)

data class Snow(
    @SerializedName("3h")
    val indicator: Double
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)