package com.kayle.mvvmweather.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kayle.mvvmweather.entity.Weathers
import com.kayle.mvvmweather.repository.OnTaskFinish
import com.kayle.mvvmweather.repository.WeatherRepository

class WeatherModel(private var weatherRepository: WeatherRepository): ViewModel() {
    private var weatherLiveData = MutableLiveData<Weathers>()
    fun callInfo():LiveData<Weathers>{
        weatherRepository.loadInfo(object :
            OnTaskFinish {
            override fun onFinish(data: Weathers?) {
                weatherLiveData.postValue(data)
            }
        })
        return weatherLiveData
    }
}