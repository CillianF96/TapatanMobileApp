// GameViewModel.kt
package com.cillian.tapatan_k00209278.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cillian.tapatan_k00209278.TapatanGameLogic
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val gameLogic = TapatanGameLogic()

    var boardState by mutableStateOf(gameLogic.resetBoard())
    var isPlayer1Turn by mutableStateOf(true)
    var player1MovesLeft by mutableStateOf(3)
    var player2MovesLeft by mutableStateOf(3)
    var winner by mutableStateOf<String?>(null)
    var isDraw by mutableStateOf(null)
    var isMovingPhase by mutableStateOf(false)
    var selectedPieceIndex by mutableStateOf<Int?>(null)
    var player1Wins by mutableStateOf(0)
    var player2Wins by mutableStateOf(0)
    var totalGamesWon by mutableStateOf(0) // New variable for total games won
    var player1Input by mutableStateOf("Player 1")
    var player2Input by mutableStateOf("Player 2")
    var isSinglePlayerMode by mutableStateOf(true)

    fun updatePlayer1Name(name: String) {
        player1Input = name
    }

    fun updatePlayer2Name(name: String) {
        player2Input = name
    }

    fun updateSinglePlayerMode(isSingle: Boolean) {
        isSinglePlayerMode = isSingle
    }

    fun resetGame() {
        boardState = gameLogic.resetBoard()
        player1MovesLeft = 3
        player2MovesLeft = 3
        isPlayer1Turn = true
        winner = null
        isDraw = null
        isMovingPhase = false
        selectedPieceIndex = null
    }

    fun resetAll() {
        resetGame()
        resetWinnersCounter()
    }

    // New function to reset the winners' counter
    fun resetWinnersCounter() {
        player1Wins = 0
        player2Wins = 0
        totalGamesWon = 0
    }

    fun onCellClick(index: Int) {
        if (winner != null || isDraw != null) return
        if (isMovingPhase) {
            handleMovingPhaseClick(index)
            return
        }
        if (boardState[index].isEmpty()) {
            boardState = boardState.toMutableList().apply {
                this[index] = if (isPlayer1Turn) "P1" else "P2"
            }
            if (gameLogic.checkWin(boardState, if (isPlayer1Turn) "P1" else "P2")) {
                winner = if (isPlayer1Turn) player1Input else player2Input
                updateWinCount()
            }
            updateMovesLeft()
            isPlayer1Turn = !isPlayer1Turn
        }
    }

    private fun handleMovingPhaseClick(index: Int) {
        if (selectedPieceIndex == null && boardState[index] == (if (isPlayer1Turn) "P1" else "P2")) {
            selectedPieceIndex = index
        } else if (selectedPieceIndex != null && boardState[index].isEmpty()) {
            boardState = boardState.toMutableList().apply {
                this[selectedPieceIndex!!] = ""
                this[index] = if (isPlayer1Turn) "P1" else "P2"
            }
            selectedPieceIndex = null
            if (gameLogic.checkWin(boardState, if (isPlayer1Turn) "P1" else "P2")) {
                winner = if (isPlayer1Turn) player1Input else player2Input
                updateWinCount()
            }
            isPlayer1Turn = !isPlayer1Turn
        }
    }

    private fun updateMovesLeft() {
        if (isPlayer1Turn) player1MovesLeft-- else player2MovesLeft--
        if (player1MovesLeft == 0 && player2MovesLeft == 0) {
            isMovingPhase = true
        }
    }

    fun aiMove() {
        if (winner != null || isDraw != null) return
        val emptyCells = boardState.indices.filter { boardState[it].isEmpty() }
        if (emptyCells.isNotEmpty()) {
            val randomIndex = emptyCells[Random.nextInt(emptyCells.size)]
            boardState = boardState.toMutableList().apply {
                this[randomIndex] = "P2"
            }
            player2MovesLeft--
            if (gameLogic.checkWin(boardState, "P2")) {
                winner = player2Input
                player2Wins++
                totalGamesWon++ // Increment total games won for each game won
            }
            if (player1MovesLeft == 0 && player2MovesLeft == 0) {
                isMovingPhase = true
            }
            isPlayer1Turn = true
        }
    }

    private fun updateWinCount() {
        if (winner == player1Input) {
            player1Wins++
        } else if (winner == player2Input) {
            player2Wins++
        }
        totalGamesWon++
    }

    fun clearWinner() {
        winner = null
    }

    fun clearDraw() {
        isDraw = null
    }
}
