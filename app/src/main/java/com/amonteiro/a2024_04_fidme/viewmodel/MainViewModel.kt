package com.amonteiro.a2024_04_fidme.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_04_fidme.model.PictureBean
import com.amonteiro.a2024_04_fidme.model.WeatherAPI
import com.amonteiro.a2024_04_fidme.utils.LocationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""


fun main() {
    val mainViewModel  = MainViewModel()
    println("Chargement des données...")

    mainViewModel.uploadSearchText("Nice")
    mainViewModel.loadWeatherAround()

    runBlocking {
        while(mainViewModel.runInProgress) {
            delay(1000)
        }
    }

    println(mainViewModel.pictureList)
    println("ErrorMessage = " + mainViewModel.errorMessage)
    println("Fin")
}

class MainViewModel : ViewModel() {

    var searchText by mutableStateOf("")
        private set
    var pictureList by mutableStateOf(emptyList<PictureBean>())
        private set
    var runInProgress by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf("")
        private set

//    init {//Création d'un jeu de donnée au démarrage
//        loadFakeData()
//    }

    fun togglePicture(data : PictureBean){
        data.favorite.value = !data.favorite.value
//        data.favorite = !data.favorite
//        // Déclenche l'observateur
        pictureList = pictureList.map { it.copy() }
    }

    fun clearFavorite(){
        pictureList.forEach { it.favorite.value = false }
    }

    fun loadWeatherAround(context: Context) {

        runInProgress = true
        errorMessage = ""

        LocationUtils.getLastKnowLocationEconomyMode(context)?.addOnSuccessListener { location->
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    pictureList = WeatherAPI.loadWeatherAround(location.latitude, location.longitude).map {
                        PictureBean(
                            it.id, it.weather.getOrNull(0)?.icon ?: "", it.name,
                            "Il fait ${it.main.temp}° à ${it.name} avec un vent de ${it.wind.speed} m/s\n"
                        )
                    }
                }
                catch(e:Exception){
                    e.printStackTrace()
                    errorMessage = e.message ?: "Une erreur est survenue"
                }
                runInProgress = false
            }
        }?.addOnFailureListener {
            it.printStackTrace()
            runInProgress = false
            errorMessage = it.message ?: "Une erreur est survenue"
        }


    }

    fun loadWeatherAround() {

        runInProgress = true
        errorMessage = ""
        viewModelScope.launch(Dispatchers.Default) {
            try {
                pictureList = WeatherAPI.loadWeatherAround(searchText).map {
                    PictureBean(
                        it.id, it.weather.getOrNull(0)?.icon ?: "", it.name,
                        "Il fait ${it.main.temp}° à ${it.name} avec un vent de ${it.wind.speed} m/s\n"
                    )
                }
            }
            catch(e:Exception){
                e.printStackTrace()
                errorMessage = e.message ?: "Une erreur est survenue"
            }

            runInProgress = false
        }
    }

    fun uploadSearchText(newText: String) {
        searchText = newText
    }

    fun loadFakeDataAsync(){
        runInProgress = true
        viewModelScope.launch(Dispatchers.Default) {
            delay(2000)
            loadFakeData()
            runInProgress = false
        }
    }

    fun loadFakeDataWithLoadingAndError() {
        loadFakeData()
        searchText = "BC"
        errorMessage = "Une erreur"
        runInProgress = true
    }

    fun loadFakeData() {
        pictureList = arrayListOf(
            PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
            PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
            PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
            PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
        ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel
    }
}