package com.ud.riddle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.riddle.models.enums.GameStateEnum
import com.ud.riddle.models.enums.Player
import com.ud.riddle.ui.theme.RiddleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RiddleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen(){
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }

    var gameState by remember { mutableStateOf(GameStateEnum.CREATING_PLAYERS) }
    val players = remember { mutableStateListOf<Player>() }

    val secret = "cactus"
    // Si no usamos remember se reinician  o quedan estados inconsistentes entonces:
    var impostorPosition by remember { mutableStateOf(-1) } //Guardamos la posicion del impostor
    var positionClue by remember { mutableStateOf(0) } // Guardamos la posicion de la pista


    when(gameState){

        GameStateEnum.CREATING_PLAYERS -> {

            Column(modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Input a name a player")
                TextField(
                    label = { Text("Label") },
                    value = name,
                    onValueChange = {
                        name = it
                    }
                )

                Button(onClick = {
                    players.add(Player(name=name))
                    name = ""
                }) { Text("Add") }

                Button(onClick = {
                    players.shuffle()
                    impostorPosition = players.indices.random()
                    players[impostorPosition].isImpostor = true

                    gameState = GameStateEnum.SHOWING_CLUE
                }) { Text("Start") }

                if (players.isNotEmpty()){
                    Text("Players:")

                    for (player in players){
                        Text(player.name)
                    }
                }

            }

        }
        GameStateEnum.SHOWING_CLUE -> {
            val currentPlayer = players[positionClue]
            Column(modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Take phone ${currentPlayer.name}")

                Button(onClick = {
                    val isImpostor =  currentPlayer.isImpostor

                    if (isImpostor){
                        Toast.makeText(context, "Eres el impostor", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Pista: $secret", Toast.LENGTH_LONG).show()
                    }
                }) { Text("Show Clue") }


                Button(onClick = {
                    //currentPlayer = players[positionClue] Lo quitamos porque estamos haciendo reasignacion
                    positionClue++

                    if (positionClue == players.size){
                        positionClue = 0
                        gameState = GameStateEnum.IN_TURNS
                    }

                }) { Text("Next") }

            }

        }
        GameStateEnum.IN_TURNS -> {
            val currentPlayer = players[positionClue]
            Column(modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text("Turn of: ${currentPlayer.name}")

                Button(onClick = {

                    gameState = GameStateEnum.END

                }) { Text("Show Impostor") }

                Button(onClick = {
                    positionClue++

                    if (positionClue == players.size){
                        positionClue = 0
                    }

                }) { Text("Next") }
            }

        }
        GameStateEnum.END -> {
            val impostor = players[impostorPosition] // Creamos una variable impostor, y buscamos el jugador en la lista players y en la posicion del impostor
            Column(modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text("The impostor era: ${impostor.name}")

            }
        }

    }
}