package com.ud.riddle.data

object WordsRepository {
    private val words= listOf(
        "pizza","hospital","discoteca","playa","colegio",
        "aeropuerto","nave espacial","supermercado",
        "selva","desierto","volcán","castillo",
        "circo","barco","casino",
        "zombie","pirata","ninja","robot","alien",
        "dragón","tesoro","isla","cueva","laboratorio"
    )
    fun getRandom(): String{
        return words.random()
    }
}