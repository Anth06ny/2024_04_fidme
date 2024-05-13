package com.amonteiro.a2024_04_fidme.exo

fun main() {
    exo2()
}


data class PersonBean(var name: String, var note: Int)

fun exo3() {
    val list = arrayListOf(PersonBean("toto", 16), PersonBean("Bobby", 20), PersonBean("Toto", 8), PersonBean("Charles", 14))

    //TODO
    println("\n\nAfficher combien il y a de Toto dans la classe ?")
    val isToto: (PersonBean) -> Boolean = { it.name.equals("toto", true) }
    println(list.count { isToto(it) })
    println(list.count(isToto))

    println("\n\nAfficher combien de Toto ayant la moyenne (10 et +)")
    println(list.count { isToto(it) && it.note >= 10 })

    println("\n\nAfficher combien de Toto ont plus que la moyenne de la classe")
    val average = list.map { it.note }.average()
    println(list.count { isToto(it) && it.note >= average })

    println("\n\nAfficher la list triée par nom sans doublon")
    println("\n\nAjouter un point a ceux n’ayant pas la moyenne (<10)")
    println("\n\nAjouter un point à tous les Toto")
    println("\n\nRetirer de la liste ceux ayant la note la plus petite. (Il peut y en avoir plusieurs)")
    val minNote = list.minOf { it.note }
    list.removeIf { it.note == minNote }

    println("\n\nAfficher les noms de ceux ayant la moyenne(10et+) par ordre alphabétique")

    //TODO Créer une variable isToto contenant une lambda qui teste si un PersonBean s'appelle Toto

    println("\n\nDupliquer la liste ainsi que tous les utilisateurs (nouvelle instance) qu'elle contient")
    list.map { it.copy() }

    println("\n\nAfficher par notes croissantes les élèves ayant eu cette note comme sur l'exemple")
    println(list.groupBy { it.note }.entries.sortedBy { it.key }.joinToString("\n") {
        "${it.key} : ${it.value.joinToString { it.name }}"
    })


}

data class UserBean(var name: String, var old: Int)

fun exo2() {
    val compareUsersByName: (UserBean, UserBean) -> UserBean = { u1, u2 -> if (u1.name.lowercase() <= u2.name.lowercase()) u1 else u2 }
    val compareUsersByOld: (UserBean, UserBean) -> UserBean = { u1, u2 -> if (u1.old > u2.old) u1 else u2 }

    val user1 = UserBean("Bob", 19)
    val user2 = UserBean("Toto", 45)
    val user3 = UserBean("Charles", 26)

    println(compareUsersByName(user1, user2))
    println(compareUsersByOld(user1, user2))

    compareUsers(user1, user2, user3, compareUsersByName)

    compareUsers(user1, user2, user3) { a, b ->
        if (Math.abs(a.old - 30) <= Math.abs(b.old - 30)) a else b
    }

}

inline fun compareUsers(u1: UserBean, u2: UserBean, u3: UserBean, comparator: (UserBean, UserBean) -> UserBean) = comparator(comparator(u1, u2), u3)

fun exo1() {
    //Déclaration
    val lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    val lower2 = { it: String -> println(it.lowercase()) }
    val lower3: (String) -> Unit = { it -> println(it.lowercase()) }
    val lower4: (String) -> Unit = { println(it.lowercase()) }

    val hour: (Int) -> Int = { it / 60 }
    val max = { a: Int, b: Int -> Math.max(a, b) }
    val reverse: (String) -> String = { it.reversed() }

    var minToMinHour: ((Int?) -> Pair<Int, Int>?)? = { it?.let { Pair(it / 60, it % 60) } }

    println(minToMinHour?.invoke(123))
    println(minToMinHour?.invoke(null))
    minToMinHour = null
}

