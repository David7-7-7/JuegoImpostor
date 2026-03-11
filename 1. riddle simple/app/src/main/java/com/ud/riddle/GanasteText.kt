package com.ud.riddle

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp

@Composable
fun Ganaste(moves: Int,resultado: String,onReset: () -> Unit) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "🎉 ¡GANASTE! 🎉",
            fontSize = 30.sp
        )

        Text(
            text = "Terminaste en $moves movimientos",
            fontSize = 18.sp
        )
        Text(
            text =resultado,
            fontSize = 18.sp
        )

        Button(onClick = { onReset() }) {
            Text("Jugar otra vez")
        }
    }
}