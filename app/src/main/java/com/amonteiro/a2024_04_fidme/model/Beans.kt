package com.amonteiro.a2024_04_fidme.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.Random



var data : HouseBean? = HouseBean("Seat", 10, 12)

fun main() {

    val r = RandomName()
    val r2 = RandomName()
}

data class PictureBean(val id:Int, val url: String, val title: String, val longText: String, var favorite: MutableState<Boolean> = mutableStateOf(false) )


class RandomName {
    private val list = arrayListOf("Toto", "Tata", "Bob")
    private var oldValue = ""

    fun add(name:String?) = if(!name.isNullOrBlank() && name !in list) list.add(name) else false

    fun next() = list.random()

    fun  nextDiffv2() = list.filter { it != oldValue }.random().also  { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())


    fun nextDiff(): String {
        var newValue = next()
        while(newValue == oldValue){
            newValue = next()
        }

        oldValue = newValue
        return newValue
    }
}


class ThermometerBean(val min : Int,val max: Int, value : Int){

    var value = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    init {
        this.value = value
    }

    companion object {
        fun getCelsiusThermometer() = ThermometerBean(-30, 50, 0)
        fun getFahrenheitThermometer() = ThermometerBean(20, 120, 32)
    }
}


class PrintRandomIntBean(val max: Int) {
    private val random: Random = Random()

    init {
        repeat(3) {
            random.nextInt(max)
        }
    }


    constructor() : this(100) {
        random.nextInt(max)
    }

}

class HouseBean(var color: String, width: Int, length: Int) {
    var area = width * length


}


data class CarBean(var marque: String? = null, var model: String? = "") {
    var couleur = ""

}