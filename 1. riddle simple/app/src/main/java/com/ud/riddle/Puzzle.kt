package com.ud.riddle

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp


@Composable
fun Puzzle(){


    Column(modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("Slide Puzzle",
            fontSize = 38.sp)

        TableroNumerico()
    }
}

@Composable
fun TableroNumerico() {

    val context = LocalContext.current
    val solucion = listOf("1","2","3","4","5","6","7","8","0")
    val numeros = remember { mutableStateListOf<String>("1","2","3","4","5","6","7","8","0").apply { shuffle() } }

    var seleccion by remember { mutableStateOf<Int?>(null) } // Digamos cuando elegimo el 5 hace como una pregunta de "Ya elegiste el primer numero?"
    var moves by remember { mutableStateOf(0) }
    var Gane by remember { mutableStateOf(false) }

    fun Inicializamos(){
        numeros.clear()
        numeros.addAll(solucion)
        numeros.shuffle()
        moves= 0
        Gane = false
        seleccion = null
    }
   LaunchedEffect(Unit) { //Ejecutamos el juego automaticamente
        Inicializamos()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StyleButton("Reset") {
            Inicializamos()
        }
        Spacer(Modifier.height(20.dp))
        Text("Moves: $moves")
        Spacer(Modifier.height(40.dp))
        if(solucion == numeros){
            Gane = true
            Toast.makeText(context, "Ganaste 🎉", Toast.LENGTH_LONG).show()
        }
        Spacer(Modifier.height(40.dp))
        if (Gane) {
            Inicializamos()
        }

        Column() {
            // Creamos las 3 filas manualmente
                for (i in 0 until 3) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (j in 0 until 3) {
                        val position = i * 3 + j //Posicion de cada numero
                        val valorNum = numeros[position] // El valor del numero seleccionado

                        // Cada celda es un botón
                        Button(
                            onClick = {
                                if (seleccion == null){
                                    seleccion = position // Comprobamos que no es null
                                    //Guardamos el primer toque ( el primer boton qu eelegimos)
                                }else {
                                    val pos1 = seleccion!! // Confiamos que no sea null
                                    val pos2 = position // Guardamos el segundo movimiento

                                    val fila1 = pos1 / 3
                                    val column1 = pos1 % 3
                                    val fila2 = pos2 / 3
                                    val column2 = pos2 % 3

                                    val esAdyacente =
                                        kotlin.math.abs(fila1 - fila2) +
                                                kotlin.math.abs(column1 - column2) == 1

                                    if (esAdyacente) {
                                        val temp = numeros[pos1]
                                        numeros[pos1] = numeros[pos2]
                                        numeros[pos2] = temp
                                        moves++
                                    }
                                    seleccion = null
                                }
                            },
                            modifier = Modifier
                                .size(80.dp)
                                .padding(bottom = 8.dp)
                        ) {
                            Text(text = valorNum, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun StyleButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        Text(text)
    }
}

