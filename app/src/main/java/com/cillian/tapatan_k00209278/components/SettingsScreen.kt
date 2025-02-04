package com.cillian.tapatan_k00209278.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cillian.tapatan_k00209278.ui.theme.BlueCustom
import com.cillian.tapatan_k00209278.ui.theme.GrayBackgroud

@Composable
fun SettingsScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel = viewModel()
) {
    // Use the player names from gameViewModel
    var player1Input by remember { mutableStateOf(gameViewModel.player1Input) }
    var player2Input by remember { mutableStateOf(gameViewModel.player2Input) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroud)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Settings",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = BlueCustom
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Player 1 Name:", fontSize = 30.sp)
            TextField(
                value = player1Input,
                onValueChange = {
                    player1Input = it
                    gameViewModel.updatePlayer1Name(it)
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Player 2 Name:", fontSize = 30.sp)
            TextField(
                value = player2Input,
                onValueChange = {
                    player2Input = it
                    gameViewModel.updatePlayer2Name(it)
                }
            )
        }

        Button(
            onClick = { navController.navigate("game") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text("Confirm Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
