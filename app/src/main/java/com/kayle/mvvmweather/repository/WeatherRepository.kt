package com.kayle.mvvmweather.repository

import android.util.Log
import com.kayle.mvvmweather.entity.Weathers
import com.kayle.mvvmweather.api.AppClientManager
import com.kayle.mvvmweather.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {
    fun loadInfo(task: OnTaskFinish) {
        val apiService = AppClientManager.client.create(WeatherService::class.java)
        apiService.getWeathers().enqueue(object : Callback<Weathers> {
            override fun onResponse(call: Call<Weathers>, response: Response<Weathers>) {
                task.onFinish(response.body())
            }

            override fun onFailure(call: Call<Weathers>, t: Throwable) {
                task.onFinish(null)
            }
        })
    }
}
interface OnTaskFinish {
    fun onFinish(data: Weathers?)
}