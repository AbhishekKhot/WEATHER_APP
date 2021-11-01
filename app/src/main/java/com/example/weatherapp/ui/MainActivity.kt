package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.utils.Constatns.Companion.DELAY_TIME
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherRepository = WeatherRepository()
        val viewModelProviderFactory = WeatherViewModelProviderFactory(weatherRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(WeatherViewModel::class.java)


        viewModel.currentWeather.observe(this,{response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { currentWeatherResponse ->
                        tvDescription.text=currentWeatherResponse.weather[0].description
                        tvMaxTemperature.text=currentWeatherResponse.main.temp_max.toString()
                        tvMinTemperature.text=currentWeatherResponse.main.temp_min.toString()
                        tvSunRise.text=currentWeatherResponse.sys.sunrise.toString()
                        tvSunSet.text=currentWeatherResponse.sys.sunset.toString()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        var job: Job? = null
        edit_text_search.addTextChangedListener { searchText: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(DELAY_TIME)
                searchText?.let {
                    if(searchText.toString().isNotEmpty()){
                        viewModel.searchCurrentWeather(searchText.toString())
                    }
                }
            }
        }

        viewModel.searchWeather.observe(this,{response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { searchResponse ->
                        tvDescription.text=searchResponse.weather[0].description
                        tvMaxTemperature.text=searchResponse.main.temp_max.toString()
                        tvMinTemperature.text=searchResponse.main.temp_min.toString()
                        tvSunRise.text=searchResponse.sys.sunrise.toString()
                        tvSunSet.text=searchResponse.sys.sunset.toString()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progressBar.visibility= View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility= View.VISIBLE
    }
}