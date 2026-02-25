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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ud.riddle.data.WordsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RiddleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen(innerPadding)
                }
            }
        }
    }
}
@Composable
fun GameScreen(padding: PaddingValues){
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }

    var gameState by remember { mutableStateOf(GameStateEnum.CREATING_PLAYERS) }
    val players = remember { mutableStateListOf<Player>() }

    val secret = WordsRepository.getRandom()
    var impostorPosition: Int? = 0
    var positionClue by remember { mutableStateOf(0) }
    var currentPlayer: Player?


    Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

    when(gameState){

        GameStateEnum.CREATING_PLAYERS -> {
                Text(text = "IMPOSTOR GAME",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold)

                Text(text="Add player to start",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium)

                Spacer(Modifier.height(16.dp))

                TextField(
                    label = { Text("Name") },
                    value = name,
                    onValueChange = { name = it }
                )

                Spacer(Modifier.height(16.dp))

                StyleButton("Add"){
                    players.add(Player(name=name))
                    name = ""
                }

               Spacer(Modifier.height(16.dp))

                StyleButton("Start"){
                    players.shuffle()
                    impostorPosition = players.indices.random()
                    players[impostorPosition].isImpostor = true

                    gameState = GameStateEnum.SHOWING_CLUE
                }

                if (players.isNotEmpty()){
                    Text("Players:")

                    for (player in players){
                        Text(player.name)
                    }
                }
        }
        GameStateEnum.SHOWING_CLUE -> {
            currentPlayer = players[positionClue]

                Text(text="Take phone ${currentPlayer?.name}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(16.dp))

                StyleButton("Show Clue") {
                    val isImpostor =  currentPlayer?.isImpostor

                    if (isImpostor == true){
                        Toast.makeText(context, "No tienes pista, eres el impostor", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Pista: $secret", Toast.LENGTH_LONG).show()
                    }
                }

                Spacer(Modifier.height(16.dp))

                StyleButton("Next") {
                    currentPlayer = players[positionClue]
                    positionClue++

                    if (positionClue == players.size){
                        positionClue = 0
                        gameState = GameStateEnum.IN_TURNS
                    }
                }
        }
        GameStateEnum.IN_TURNS -> {
            currentPlayer = players[positionClue]

                Text(text="Vota: ${currentPlayer?.name}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(16.dp))

                StyleButton("Next") {
                    currentPlayer = players[positionClue]
                    positionClue++

                    if (positionClue == players.size) {
                        gameState = GameStateEnum.END
                    }
                }
        }
        GameStateEnum.END -> {
                StyleButton("Show impostor") {
                    val nameImpostor = impostorPosition?.let { players[it] }?.name
                    Toast.makeText(context, "Impostor $nameImpostor", Toast.LENGTH_LONG).show()

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