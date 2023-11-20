package com.example.weatherreport.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weatherreport.data.entities.City
import com.example.weatherreport.data.entities.WeatherEntity
import com.example.weatherreport.data.entities.WeatherSummaryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [WeatherSummaryEntity::class], version = 1, exportSchema = false)
@TypeConverters(CityConverter::class, Converters::class)

abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        val DB_NAME = "main.db"
        private val Lock = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(Lock) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun weatherDao(): WeatherSummaryDao
}

class CityConverter {
    @TypeConverter
    fun fromCity(city: City): String {
        val gson = Gson()
        return gson.toJson(city)
    }

    @TypeConverter
    fun toCity(cityString: String): City {
        val gson = Gson()
        return gson.fromJson(cityString, City::class.java)
    }
}

class Converters {
    @TypeConverter
    fun fromString(value: String): List<WeatherEntity> {
        val listType = object : TypeToken<List<WeatherEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<WeatherEntity>): String {
        return Gson().toJson(list)
    }
}