package com.example.weatherreport

import android.app.Application
import com.example.weatherreport.di.AppComponent
import com.example.weatherreport.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy { DaggerAppComponent.create() }
}