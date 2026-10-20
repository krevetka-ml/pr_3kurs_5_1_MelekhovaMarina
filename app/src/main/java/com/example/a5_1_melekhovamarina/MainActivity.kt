package com.example.a5_1_melekhovamarina

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

data class Main(val temp: Double, val pressure: Int)
data class Wind(val speed: Double)
data class Weather(val name: String, val main: Main, val wind: Wind)

class MainActivity : AppCompatActivity() {

    var key = "f51b77abe3719434561f6214290abf74"

    lateinit var city: EditText
    lateinit var button: Button
    lateinit var cityName: TextView
    lateinit var temp: TextView
    lateinit var pressure: TextView
    lateinit var wind: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.city)
        button = findViewById(R.id.button)
        cityName = findViewById(R.id.cityName)
        temp = findViewById(R.id.temp)
        pressure = findViewById(R.id.pressure)
        wind = findViewById(R.id.wind)

        button.setOnClickListener {
            getWeather()
        }
    }

    fun getWeather() {
        var url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                city.text.toString() + "&appid=" + key + "&units=metric&lang=ru"

        val queue = Volley.newRequestQueue(this)

        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                Log.d("WEATHER", response)

                val weather = Gson().fromJson(response, Weather::class.java)

                Log.d("WEATHER", "Город: " + weather.name)
                Log.d("WEATHER", "Температура: " + weather.main.temp)

                cityName.text = "Город: " + weather.name
                temp.text = "Температура: " + weather.main.temp + " °C"
                pressure.text = "Давление: " + weather.main.pressure + " гПа"
                wind.text = "Скорость ветра: " + weather.wind.speed + " м/с"
            },
            { error ->
                Log.d("WEATHER", "Ошибка: " + error.toString())
                Toast.makeText(this, "Ошибка, проверьте город и ключ", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }
}