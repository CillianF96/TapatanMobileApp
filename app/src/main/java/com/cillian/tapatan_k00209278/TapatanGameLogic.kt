package com.cillian.tapatan_k00209278

class TapatanGameLogic {

    private val winningCombos = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    private val moveHistory = mutableListOf<Pair<Int, String>>()

    fun checkWin(boardState: List<String>, playerMarker: String): Boolean {
        return winningCombos.any { combo ->
            combo.all { index -> boardState[index] == playerMarker }
        }
    }

    fun resetBoard(): MutableList<String> {
        moveHistory.clear()
        return MutableList(9) { "" }
    }
}
