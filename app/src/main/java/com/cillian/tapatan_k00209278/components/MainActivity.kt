package com.cillian.tapatan_k00209278.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val gameViewModel: GameViewModel = viewModel()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                NavigationComponent(navController, gameViewModel)
            }
        }
    }
}

@Composable
fun NavigationComponent(
    navController: NavHostController,
    gameViewModel: GameViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, gameViewModel)
        }

        composable("settings") {
            SettingsScreen(navController, gameViewModel)
        }

        composable("game") {
            GameScreen(navController, gameViewModel)
        }
    }
}
