package com.amonteiro.a2024_04_fidme.exo

fun main() {

    var res = boulangerie(nbBag = 5) // boulangerie(0, 5, 0)
    println("res=$res")

//    println(scanNumber("Donnée : "))
//
//    println("fin")




}

fun scanNumber(question: String) = scanText(question).toIntOrNull() ?: 0

fun scanText(question: String): String {
    print(question)
    return readlnOrNull() ?: "-"
}


fun boulangerie(nbC: Int = 0, nbBag: Int = 0, nbSand: Int = 0) =
    nbC * PRIX_CROISSANT + nbBag * PRIX_BAGUETTE + nbSand * PRIX_SANDWITCH


fun pair(c: Int) = c % 2 == 0
fun myPrint(chain: String) = println("#$chain#")

fun toto() {

}