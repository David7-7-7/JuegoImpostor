package com.ud.riddle

import kotlin.math.abs

fun calcularMovimientosMinimos(tablero: List<String>): Int { // Obtenemos la lista de numeros desordanada

    var distancia = 0

    for (i in tablero.indices) { // Recorremos cada celda automaticamente, Indices ya sabe el numero de posiciones de la lista
        val valor = tablero[i] // Obtenemos el numero de la lista de cada posicion
        // 1,2,3,4,5,8,6,7,0
        if (valor != "0") {
            // Siempre vamos a obtener la posicion del numero restandole 1 ya que siempre empieza en 0
            val posnumobj = valor.toInt() - 1 //Deberia estar en la posicion 7 el  numero 8
            val filaActual = i / 3 //i=5 entonces 1
            val colActual = i % 3 //2
            //El numero debe estar en:
            val filaObjetivo = posnumobj / 3 // posnumobj= 7 entonces = 2
            val colObjetivo = posnumobj % 3 //1

            distancia += abs(filaActual - filaObjetivo) +
                    abs(colActual - colObjetivo)
        }
    }
    return distancia // Enviamos el valor total de las distancias
}