package com.example.weatherapp.repository

import com.example.weatherapp.api.RetrofitInstance

class WeatherRepository {
    suspend fun getCurrentWeather(cityName: String) =
        RetrofitInstance.api.getCurrentWeather(cityName)

    suspend fun searchForCurrentWeather(searchQuery: String) =
        RetrofitInstance.api.searchForCurrentWeather(searchQuery)
}