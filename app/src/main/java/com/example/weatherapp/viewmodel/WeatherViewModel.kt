package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel(private val weatherRepository: WeatherRepository) :ViewModel() {

    val currentWeather: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    val searchWeather: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    init {
        getCurrentWeather("London")
    }

    fun getCurrentWeather(cityName: String) = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        val response = weatherRepository.getCurrentWeather(cityName)
        currentWeather.postValue(setupCurrentWeatherResponse(response))
    }

    fun searchCurrentWeather(searchQuery:String) = viewModelScope.launch {
        searchWeather.postValue(Resource.Loading())
        val response = weatherRepository.searchForCurrentWeather(searchQuery)
        searchWeather.postValue(SetupSearchCurrentWeatherResponse(response))
    }

    private fun setupCurrentWeatherResponse(response: Response<WeatherResponse>) : Resource<WeatherResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun SetupSearchCurrentWeatherResponse(response: Response<WeatherResponse>) : Resource<WeatherResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}