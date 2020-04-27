package com.kayle.mvvmweather.factory

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kayle.mvvmweather.model.WeatherModel
import com.kayle.mvvmweather.repository.WeatherRepository

class WeatherFactory(private var weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherModel::class.java)) {
            return WeatherModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
