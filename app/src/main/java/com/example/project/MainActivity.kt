package com.example.project

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.project.model.DataModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    lateinit var city: TextView
    lateinit var temperature: TextView
    lateinit var date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        city = findViewById<TextView>(R.id.city);
        temperature = findViewById<TextView>(R.id.temperature)
        date = findViewById<TextView>(R.id.date)
        val responseLayout = findViewById<LinearLayout>(R.id.response)
        val editCityName = findViewById<TextView>(R.id.editCityName);
        editCityName.setOnClickListener(View.OnClickListener {
            responseLayout.setVisibility(View.VISIBLE)
            getData();
        });
    }

    fun getData(){
        try{
        val url = "http://api.openweathermap.org/data/2.5/weather?q=Pune&appid=094aa776d64c50d5b9e9043edd4ffd00"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        runBlocking {
            withContext(Dispatchers.IO){
                client.newCall(request).execute().use {
                        response -> if(!response.isSuccessful)
                    throw IOException("Unexpected code $response")
                    val gson = Gson()
                    Log.d("Data from api--------: ", ""+response.toString())
                    val body = response.body.toString();
                    var data = gson.fromJson(body,DataModel::class.java)
                    city.text = data.name
                    temperature.text = data.main.temp.toString()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        date.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString()
                    }
                }
            }
        }} catch (error:Exception){
            Log.d("Error : ","Some error occure "+error);
        }

    }
}