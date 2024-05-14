package com.amonteiro.a2024_04_fidme.model

import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader



var toto :String? = "toto"

fun main() {
    //Partie 2
    WeatherAPI.loadWeatherAround("Bordeaux").forEach {
        println("Il fait ${it.main.temp}° à ${it.name} avec un vent de ${it.wind.speed} m/s\n")
        println(it.weather.getOrNull(0)?.icon)
    }
}


object WeatherAPI {


    //Attribut instancié 1 seule fois car c'est un singleton
    //Et uniquement à la 1er utilisation (Lazy Loading)
    private val client = OkHttpClient()
    private val gson = Gson()

    const val URL_API = "https://api.openweathermap.org/data/2.5"
    const val API_KEY = "appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr"

    fun loadWeatherAround(lat:Double, lon:Double): List<WeatherBean> {
        //Réaliser la requête.
        val json: String = sendGet("https://api.openweathermap.org/data/2.5/find?lat=$lat&lon=$lon&cnt=5&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        return gson.fromJson(json, WeatherAroundResult::class.java).list.onEach {
            it.weather.getOrNull(0)?.let {
                it.icon = "https://openweathermap.org/img/wn/${ it.icon}@4x.png"
            }
        }
    }

    fun loadWeatherAround(cityName: String): List<WeatherBean> {
        val json: String = sendGet("$URL_API/find?$API_KEY&q=$cityName")
        val list =  gson.fromJson(json, WeatherAroundResult::class.java).list

        list.forEach {
            it.weather.getOrNull(0)?.let {
                it.icon = "https://openweathermap.org/img/wn/${ it.icon}@4x.png"
            }
        }

        return list
    }

    fun loadWeather(cityName: String): WeatherBean {
        val json: String = sendGet("$URL_API/weather?$API_KEY&q=$cityName")
        return gson.fromJson(json, WeatherBean::class.java).apply {
            weather.getOrNull(0)?.let {
                it.icon = "https://openweathermap.org/img/wn/${ it.icon}@4x.png"
            }
        }
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

data class WeatherAroundResult(var list:List<WeatherBean>)

data class WeatherBean(
    var id: Int,
    var main: TempBean,
    var name: String,
    var wind: WindBean,
    var weather : List<DescriptionBean>
)

data class TempBean(
    var temp: Double
)

data class WindBean(
    var speed: Double
)
data class DescriptionBean(
    var description: String,
    var icon: String,
    var main: String
)

