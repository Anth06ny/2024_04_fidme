package com.amonteiro.a2024_04_fidme.exo

import com.amonteiro.a2024_04_fidme.model.CarBean
import com.google.gson.Gson

fun main() {
    var c : CarBean? = null
    println(c.toJson())
    CarBean("Seat", "Leon").toJson().println()
}

fun String.println() = kotlin.io.println(this)

fun Any?.toJson() =if(this != null) Gson().toJson(this) else "{}"
fun Any?.toJson2() = this?.let { Gson().toJson(this) } ?: "{}"