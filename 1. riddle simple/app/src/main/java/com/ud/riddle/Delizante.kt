package com.ud.riddle

import android.R.attr.text
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ud.riddle.models.enums.GameStateEnum
import com.ud.riddle.models.enums.Player
import com.ud.riddle.ui.theme.RiddleAppTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.ud.riddle.data.WordsRepository
import org.intellij.lang.annotations.JdkConstants


@Composable
fun Deslizante(){


    Column(modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("Slide Puzzle",
            fontSize = 38.sp)

        Spacer(Modifier.height(40.dp))
        Text("Moves: ",
            fontSize = 38.sp)

        Spacer(Modifier.height(40.dp))
        StyleButton("Start Game") {

        }
    }
    Spacer(Modifier.height(40.dp))
    TableroNumerico()

}

@Composable
fun TableroNumerico() {
    // 1. El ESTADO: Si esta lista cambia, la UI se redibuja sola
    val numeros = remember { mutableStateListOf<String>("0","1", "2", "3", "4", "5", "6", "7", "8") }
    numeros.random()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Creamos las 3 filas manualmente
        for (i in 0 until 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0 until 3) {
                    val position = i * 3 + j //Posicion de cada numero
                    val valor = numeros[position]

                    // Cada celda es un botón
                    Button(
                        onClick = {
                            //intercambiarConSiguiente(numeros, position)
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        Text(text = valor, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}


@Composable
fun intercambiarConSiguiente(lista: MutableList<String>, position: Int) {
    // Si no es el último elemento, lo intercambiamos con el siguiente
    if (position < lista.size - 1) {
        val temp = lista[position]
        lista[position] = lista[position + 1]
        lista[position + 1] = temp
    } else {
        // Si es el 9, lo mandamos al principio para que "rote"
        val ultimo = lista.removeAt(position)
        lista.add(0, ultimo)
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
