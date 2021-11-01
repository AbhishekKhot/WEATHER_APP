package com.example.weatherapp.api

import com.example.weatherapp.utils.Constatns.Companion.API_KEY
import com.example.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {


    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q")
        cityName: String,
        @Query("appid")
        apiKey: String = API_KEY
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun searchForCurrentWeather(
        @Query("q")
        searchQuery: String,
        @Query("appid")
        apiKey: String = API_KEY
    ): Response<WeatherResponse>
}