package com.example.weatherreport.data.entities

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Long
)

data class Coord(
    val lat: Double,
    val lon: Double
)