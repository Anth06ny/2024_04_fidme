package com.amonteiro.a2024_04_fidme.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_04_fidme.model.PictureBean
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
    mainViewModel.loadFakeDataAsync()

    runBlocking {
        while(mainViewModel.runInProgress) {
            delay(1000)
        }
    }

    println(mainViewModel.pictureList)
    println("Fin")
}

class MainViewModel : ViewModel() {

    var searchText by mutableStateOf("")
        private set
    var pictureList by mutableStateOf(emptyList<PictureBean>())
        private set
    var runInProgress by mutableStateOf(false)
        private set

//    init {//Création d'un jeu de donnée au démarrage
//        loadFakeData()
//    }

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

    fun loadFakeData() {
        pictureList = arrayListOf(
            PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
            PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
            PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
            PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
        ).shuffled() //shuffled() pour avoir un ordre différent à chaque appel
    }
}