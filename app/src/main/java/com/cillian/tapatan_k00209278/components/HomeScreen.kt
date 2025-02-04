package com.cillian.tapatan_k00209278.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.cillian.tapatan_k00209278.ui.theme.BlueCustom
import com.cillian.tapatan_k00209278.ui.theme.GrayBackgroud

@Composable
fun HomeScreen(
    navController: NavHostController,
    gameViewModel: GameViewModel
) {
    var isSinglePlayer by remember { mutableStateOf(gameViewModel.isSinglePlayerMode) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBackgroud)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "TapATan",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = BlueCustom
        )

        Text(text = "Select Number of Players", fontSize = 30.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    isSinglePlayer = true
                    gameViewModel.updateSinglePlayerMode(true)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSinglePlayer) BlueCustom else Color.Gray
                )
            ) { Text("Single", fontSize = 24.sp, color = Color.White) }

            Button(
                onClick = {
                    isSinglePlayer = false
                    gameViewModel.updateSinglePlayerMode(false)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isSinglePlayer) BlueCustom else Color.Gray
                )
            ) { Text("2-Player", fontSize = 24.sp, color = Color.White) }
        }

        Button(
            onClick = { navController.navigate("game") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Begin the Game", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
