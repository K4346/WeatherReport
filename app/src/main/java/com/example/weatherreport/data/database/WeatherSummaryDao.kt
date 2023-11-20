package com.example.weatherreport.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherreport.data.entities.WeatherSummaryEntity

@Dao
interface WeatherSummaryDao {
    @Query("SELECT * FROM weather_summary")
    fun getWeatherSummary(): LiveData<WeatherSummaryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: WeatherSummaryEntity)

}