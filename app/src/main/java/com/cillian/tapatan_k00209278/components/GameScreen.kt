package com.cillian.tapatan_k00209278.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cillian.tapatan_k00209278.R
import com.cillian.tapatan_k00209278.ui.theme.BlueCustom
import com.cillian.tapatan_k00209278.ui.theme.GrayBackgroud

@Composable
fun GameScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current

    LaunchedEffect(gameViewModel.isPlayer1Turn) {
        if (gameViewModel.isSinglePlayerMode && !gameViewModel.isPlayer1Turn) {
            gameViewModel.aiMove()
        }
    }

    if (gameViewModel.winner != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.clearWinner() },
            title = { Text("Congratulations!") },
            text = { Text("${gameViewModel.winner} wins the game!") },
            confirmButton = { Button(onClick = { gameViewModel.resetGame() }) { Text("Play Again") } }
        )
    }

    if (gameViewModel.isDraw != null) {
        AlertDialog(
            onDismissRequest = { gameViewModel.clearDraw() },
            title = { Text("It's a Draw!") },
            text = { Text("The game ended in a draw!") },
            confirmButton = { Button(onClick = { gameViewModel.resetGame() }) { Text("Play Again") } }
        )
    }

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeGameScreen(navController, gameViewModel)
    } else {
        PortraitGameScreen(navController, gameViewModel)
    }
}

@Composable
fun PortraitGameScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroud)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        GameHeader(navController, gameViewModel::resetGame, gameViewModel::resetAll)
        TurnIndicator(
            isPlayer1Turn = gameViewModel.isPlayer1Turn,
            player1Name = gameViewModel.player1Input,
            player2Name = gameViewModel.player2Input
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(10.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(GrayBackgroud),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tapatanboard),
                contentDescription = "Tapatan Board",
                modifier = Modifier.fillMaxSize()
            )
            BoardGrid(boardState = gameViewModel.boardState, onCellClick = gameViewModel::onCellClick)
        }

        PlayerRow(
            playerName = gameViewModel.player1Input,
            playerWins = gameViewModel.player1Wins,
            playerIcon = R.drawable.player1
        )
        PlayerRow(
            playerName = gameViewModel.player2Input,
            playerWins = gameViewModel.player2Wins,
            playerIcon = R.drawable.player2
        )

        Button(
            onClick = { gameViewModel.resetWinnersCounter() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("New Game (Reset Counters)", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun LandscapeGameScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroud)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameHeader(navController, gameViewModel::resetGame, gameViewModel::resetAll)
            TurnIndicator(
                isPlayer1Turn = gameViewModel.isPlayer1Turn,
                player1Name = gameViewModel.player1Input,
                player2Name = gameViewModel.player2Input
            )
            PlayerRow(
                playerName = gameViewModel.player1Input,
                playerWins = gameViewModel.player1Wins,
                playerIcon = R.drawable.player1
            )
            PlayerRow(
                playerName = gameViewModel.player2Input,
                playerWins = gameViewModel.player2Wins,
                playerIcon = R.drawable.player2
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1.5f)
                .shadow(5.dp, RoundedCornerShape(15.dp))
                .clip(RoundedCornerShape(15.dp))
                .background(GrayBackgroud),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tapatanboard),
                contentDescription = "Tapatan Board",
                modifier = Modifier.fillMaxSize()
            )
            BoardGrid(boardState = gameViewModel.boardState, onCellClick = gameViewModel::onCellClick)
        }
    }
}

@Composable
fun BoardGrid(boardState: List<String>, onCellClick: (Int) -> Unit) {
    Column {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    CheckerCell(player = boardState[index], onClick = { onCellClick(index) })
                }
            }
        }
    }
}

@Composable
fun CheckerCell(player: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        when (player) {
            "P1" -> Image(painter = painterResource(id = R.drawable.player1), contentDescription = "Player 1 Checker")
            "P2" -> Image(painter = painterResource(id = R.drawable.player2), contentDescription = "Player 2 Checker")
        }
    }
}

@Composable
fun PlayerRow(playerName: String, playerWins: Int, playerIcon: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val winText = if (playerWins == 1) "$playerName has 1 win" else "$playerName has $playerWins wins"
        Text(text = winText, fontSize = 24.sp)
        Image(painter = painterResource(id = playerIcon), contentDescription = "$playerName Icon")
    }
}

@Composable
fun GameHeader(navController: NavHostController, resetGame: () -> Unit, resetAll: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "TapATan",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = BlueCustom
        )
        IconButton(onClick = resetGame) { Icon(Icons.Default.Refresh, contentDescription = "Refresh") }
        IconButton(onClick = { navController.navigate("home") }) { Icon(Icons.Default.Home, contentDescription = "Home") }
        IconButton(onClick = { navController.navigate("settings") }) { Icon(Icons.Default.Settings, contentDescription = "Settings") }
    }
}

@Composable
fun TurnIndicator(isPlayer1Turn: Boolean, player1Name: String, player2Name: String) {
    val turnMessage = if (isPlayer1Turn) "$player1Name's Turn" else "$player2Name's Turn"
    Text(
        text = turnMessage,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(16.dp)
    )
}
