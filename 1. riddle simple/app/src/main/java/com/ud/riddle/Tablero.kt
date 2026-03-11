package com.ud.riddle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun TableroNumerico(viewModel: PuzzleViewModel = viewModel()) { //Creamos viewModel de tipo Puzzle, entonces viewModel guarda el estado del juego,

    Column(modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(Modifier.height(60.dp))
        Text("SLIDE PUZZLE",
            fontSize = 38.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        val numeros = viewModel.numeros // Traemos la lista de numeros del tablero
        val seleccion = viewModel.seleccion.value // Guardamos la celda que selecciono el jugador
        val moves = viewModel.moves.value
        val gane = viewModel.gane.value // Boleano
        val movimientosMinimos = viewModel.movimientosMinimos.value
        val resultado = viewModel.evaluarResultado()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { viewModel.inicializar() },
                modifier = Modifier.fillMaxWidth().height(55.dp)
            ) {
                Text("REINICIAR")
            }

            Spacer(Modifier.height(20.dp))
            Text("Movimientos: $moves")
            Text("Movimientos Minimos: $movimientosMinimos")
            Spacer(Modifier.height(40.dp))

            if (gane) { // Si es verdadero entonces corremos la funcion Inicializar y mandamos los movimientos
                Ganaste(
                    moves,
                    resultado = resultado, //Mostramos el resutlado de los movimientos - mov minimos
                    onReset = { viewModel.inicializar() } // Llamamos la funcion Inicializar
                )
            }

            Spacer(Modifier.height(40.dp))

            Column {
                for (i in 0 until 3) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { //
                        for (j in 0 until 3) {

                            val position = i * 3 + j // Posicion del numero en la fila
                            val valorNum = numeros[position] // Guardamos el valor del numero en esa posicion

                            val borde = when {
                                seleccion == position ->
                                    BorderStroke(3.dp, Color.Red)// Si la celda esta seleccionada ponemos color rojo
                                else ->
                                    BorderStroke(1.dp, Color.Green)
                            }

                            // Creamos cada casilla de los numeros
                            Button(
                                onClick = {
                                    viewModel.seleccionarCelda(position) //Cuando el jugador toca una tecla se ejecuta la funcion seleccionarCelda
                                },
                                border = borde, // Les aplicamos el borde a las casillas
                                modifier = Modifier.size(80.dp).padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = valorNum,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}