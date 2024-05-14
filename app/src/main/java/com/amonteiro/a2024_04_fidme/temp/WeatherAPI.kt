package com.amonteiro.a2024_04_fidme.temp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    val viewModel = MainViewModel()
    viewModel.loadWeathers("")

    while(viewModel.runInProgress) {
        Thread.sleep(500)
    }

    //Affichage
    println(viewModel.dataList)
    println(viewModel.errorMessage)

}


const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""

class MainViewModel : ViewModel() {
    var dataList by mutableStateOf<List<PictureBean>>(emptyList())
    var errorMessage by mutableStateOf("")
    var runInProgress by mutableStateOf(false)
    var searchText by mutableStateOf("")

//    init {//Création d'un jeu de donnée au démarrage
//        loadFakeData()
//    }

    fun loadFakeData(){
        dataList = listOf(PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
            PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
            PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
            PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
        ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel
    }

    fun loadWeathers(cityName: String?) {
        runInProgress = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                if(cityName == null || cityName.length < 3){
                    throw Exception("Il faut au moins 3 caractères")
                }
                dataList = WeatherAPI.loadWeathers(cityName).map {
                    PictureBean(it.id, it.weather.getOrNull(0)?.icon ?: "",
                        it.name,
                        "Il fait ${it.main.temp}° à ${it.name}(id=${it.id}) avec un vent de ${it.wind.speed} m/s\n"
                        )
                }
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
data class PictureBean(val id:Int, val url: String, val title: String, val longText: String)


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