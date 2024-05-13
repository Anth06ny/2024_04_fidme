package com.amonteiro.a2024_04_fidme.temp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.InputStreamReader

//fun main() {
//    //Partie 2
//    val list = WeatherAPI.loadWeathers("Nice")
//    for(w in list){
//        println("Il fait ${w.main.temp}° à ${w.name}(id=${w.id}) avec un vent de ${w.wind.speed} m/s\n")
//        println("-Description : ${w.weather.getOrNull(0)?.description}")
//        println("-Icon : ${w.weather.getOrNull(0)?.icon}")
//    }
//
//
//}

fun main() {

    runBlocking {
        println("1")
        launch {
            delay(500)
            println("2")
        }

        println("3")
        launch {
            delay(1000)
            println("4")
        }

        println("5")
    }


    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")

    while(viewModel.runInProgress) {
        Thread.sleep(500)
    }

    val tempList = viewModel.weatherList
    if (tempList != null) {
        for (w in tempList) {
            println("Il fait ${w.main.temp}° à ${w.name}(id=${w.id}) avec un vent de ${w.wind.speed} m/s\n")
            println("-Description : ${w.weather.getOrNull(0)?.description}")
            println("-Icon : ${w.weather.getOrNull(0)?.icon}")
        }
    }
    if(viewModel.errorMessage.isNotBlank()) {
        println(viewModel.errorMessage)
    }
}


class MainViewModel : ViewModel() {
    var weatherList: List<WeatherBean>? = null
    var errorMessage = ""
    var runInProgress = false

    fun loadWeathers(cityName: String?) {
        runInProgress = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                if(cityName == null || cityName.length < 3){
                    throw Exception("Il faut au moins 3 caractères")
                }
                weatherList = WeatherAPI.loadWeathers(cityName)
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.message ?: "Une erreur est survenue"
            }
            runInProgress = false
        }
    }
}


object WeatherAPI {


    //Attribut instancié 1 seule fois car c'est un singleton
    //Et uniquement à la 1er utilisation (Lazy Loading)
    private val client = OkHttpClient()
    private val gson = Gson()

    const val URL_API = "https://api.openweathermap.org/data/2.5/find?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

    fun loadWeathers(cityName: String): List<WeatherBean> {
        val json: String = sendGet(URL_API + cityName)
        return gson.fromJson(json, WeatherAroundBean::class.java).list
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

//Objet de base retourné par l'API
data class WeatherAroundBean(var list: List<WeatherBean>)

//Ici je n'ai mis que ce qui est utile pour l'affichage demandé mais on peut tout mettre
data class WeatherBean(
    var id: Int,
    var name: String,
    var main: TempBean,
    var wind: WindBean,
    var weather: List<DescriptionBean>
)

data class TempBean(var temp: Double)
data class WindBean(var speed: Double)
data class DescriptionBean(var description: String, var icon: String)