package com.ud.riddle

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class PuzzleViewModel : ViewModel() {
    val solucion = listOf("1","2","3","4","5","6","7","8","0")
    val numeros = mutableStateListOf<String>() // Mutable nos ayuda a que se actualice el UI
    var seleccion = mutableStateOf<Int?>(null)
    var moves = mutableStateOf(0)
    var gane = mutableStateOf(false)
    var movimientosMinimos = mutableStateOf(0)


    init {
        inicializar()
    }

    fun inicializar(){
        numeros.clear() //Borramos el tablero
        numeros.addAll(solucion)
        numeros.shuffle()

        movimientosMinimos.value = calcularMovimientosMinimos(numeros) // Invocamos la funcion de calcular movimientos

        moves.value = 0
        gane.value = false
        seleccion.value = null // Colocamos otra vez en null
    }

    fun seleccionarCelda(position: Int){

        if (seleccion.value == null){ // Si seleccion es nulo guardamos la primera seleccion, o el priemer boton seleccionado
            seleccion.value = position // Guardamos la primera posicion
        } else {

            val pos1 = seleccion.value!! // Aca le preguntamos que si ya no es null guarde la segunda seleccion o posicion
            val pos2 = position

            val fila1 = pos1 / 3 //1
            val column1 = pos1 % 3 // Residuo 1

            val fila2 = pos2 / 3// 1
            val column2 = pos2 % 3//2

            //Realizamos una funcion matematica para determinar si es abyasente, por lo tanto solo se movera como queremos
            val esAdyacente = abs(fila1 - fila2) +
                        abs(column1 - column2) == 1

            if (esAdyacente){
                // Hacemos el interambio de casillas
                val temp = numeros[pos1]
                numeros[pos1] = numeros[pos2]
                numeros[pos2] = temp

                moves.value++

                if(numeros.toList() == solucion){
                    gane.value = true
                }
            }
            seleccion.value = null // Reiniciamos el boton selccionar para el siguiente movimiento
        }
    }

    fun evaluarResultado(): String { // Evaluamos si los movimientos del jugador fueron buenos o malos
        var Resultado = moves.value - movimientosMinimos.value
        return when{
            Resultado == 0 -> "Perfecto, igualaste la meta minima "
            Resultado in 1..10 ->"Muy bien, estuviste cerca "
            else -> "Felcidades, lo superaste facilmente "
        }
       // Resultado = 0
    }
}