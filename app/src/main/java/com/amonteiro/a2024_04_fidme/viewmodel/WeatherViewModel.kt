package com.amonteiro.a2024_04_fidme.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a2024_04_fidme.model.WeatherAPI
import com.amonteiro.a2024_04_fidme.model.WeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    val weather = MutableLiveData<WeatherBean?>(null)
    val errorMessage = MutableLiveData("")
    val runInProgress = MutableLiveData(false)

    fun loadWeather(cityName: String) {
        errorMessage.postValue("")
        runInProgress.postValue( true)
        weather.postValue(null)

        viewModelScope.launch(Dispatchers.Default) {
            try {
                weather.postValue(WeatherAPI.loadWeather(cityName))
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message ?: "Une erreur est survenue")
            }
            runInProgress.postValue(false)
        }
    }

}