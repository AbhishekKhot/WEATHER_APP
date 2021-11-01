package com.example.weatherapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        supportActionBar?.hide()

        lifecycleScope.launch {
            startActivity(Intent(this@SplachActivity,MainActivity::class.java))
            delay(2000L)
        }
    }
}