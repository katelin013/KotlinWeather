package com.kayle.mvvmweather.api

import com.kayle.mvvmweather.entity.Weathers
import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {
    @GET("F-C0032-001?Authorization=CWB-00B0F053-AEE0-46F7-A9A9-069F0A9D514D")
    fun getWeathers(): Call<Weathers>
}