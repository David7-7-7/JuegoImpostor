package com.ud.riddle

import kotlin.math.abs

fun calcularMovimientosMinimos(tablero: List<String>): Int { // Obtenemos la lista de numeros desordanada

    var distancia = 0

    for (i in tablero.indices) { // Recorremos cada celda automaticamente
        val valor = tablero[i] // Obtenemos el numero de la lista de cada posicion
        if (valor != "0") { // Ignoramos el espacio vacio

            val posnumobj = valor.toInt() - 1 // obtenemos la posicion correcta
            val filaActual = i / 3
            val colActual = i % 3
            val filaObjetivo = posnumobj / 3
            val colObjetivo = posnumobj % 3

            distancia += abs(filaActual - filaObjetivo) +
                    abs(colActual - colObjetivo)
        }
    }
    return distancia // Enviamos el valor total de las distancias
}