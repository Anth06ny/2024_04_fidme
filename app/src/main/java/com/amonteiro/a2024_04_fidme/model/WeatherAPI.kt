package com.amonteiro.a2024_04_fidme.model

import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader

fun main() {
    //Partie 2
    val res = WeatherAPI.loadWeather("Bordeaux")
    println("Il fait ${res.main.temp}° à ${res.name} avec un vent de ${res.wind.speed} m/s\n")

}


object WeatherAPI {


    //Attribut instancié 1 seule fois car c'est un singleton
    //Et uniquement à la 1er utilisation (Lazy Loading)
    private val client = OkHttpClient()
    private val gson = Gson()

    const val URL_API = "https://api.openweathermap.org/data/2.5/weather?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

    fun loadWeather(cityName: String): WeatherBean {
        val json: String = sendGet(URL_API + cityName)
        return gson.fromJson(json, WeatherBean::class.java)
    }

    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }

    fun getWeathers(vararg cities:String) = flow {
        cities.forEach {
            emit(loadWeather(it))
            delay(100)
        }
    }





    fun loadWeatherOpti(cityName: String): WeatherBean {
        sendGetOpti(URL_API + cityName).use {
            val isr = InputStreamReader(it.body.byteStream())
            return gson.fromJson(isr, WeatherBean::class.java)
        }
    }

    fun sendGetOpti(url: String): Response {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().also {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
        }
    }
}

/* -------------------------------- */
// Bean
/* -------------------------------- */

data class WeatherBean(
    var main: TempBean,
    var name: String,
    var wind: WindBean
)

data class TempBean(
    var temp: Double
)

data class WindBean(
    var speed: Double
)