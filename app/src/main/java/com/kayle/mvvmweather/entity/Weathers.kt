package com.kayle.mvvmweather.entity

import com.google.gson.annotations.SerializedName

class Weathers {
    @SerializedName("success")
    var success: String = ""
    @SerializedName("result")
    var result: Result? = null
    @SerializedName("records")
    var records: Records? = null
    data class Result(
        val fields: List<Field>,
        val resource_id: String
    )

    data class Field(
        val id: String,
        val type: String
    )

    data class Records(
        val datasetDescription: String,
        val location: List<Location>
    )

    data class Location(
        val locationName: String,
        val weatherElement: List<WeatherElement>
    )
    data class WeatherElement(
        val elementName: String,
        val time: List<Time>
    )

    data class Time(
        val endTime: String,
        val parameter: Parameter,
        val startTime: String
    )

    data class Parameter(
        val parameterName: String,
        val parameterUnit: String,
        val parameterValue: String
    )
}

