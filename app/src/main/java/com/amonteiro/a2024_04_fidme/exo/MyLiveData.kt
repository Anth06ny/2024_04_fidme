package com.amonteiro.a2024_04_fidme.exo

fun main() {
    var data = MyLiveData()
    data.value = "toto"

    data.observator = {
        println(it)
    }

    data.value = "tata"

}

class MyLiveData {

    var value : String? = null
        set(newValue) {
            field = newValue
            observator?.invoke(newValue)
        }

    var observator : ((String?)->Unit)? = null
}