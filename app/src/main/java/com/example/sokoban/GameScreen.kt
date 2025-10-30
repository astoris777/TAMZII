package com.example.sokoban

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    val levelData = remember { 
        val data = GameConstants.INITIAL_LEVEL_DATA.clone()
        GameLogic.initializeLevel(data)
        mutableStateOf(data)
    }
    
    Box(modifier = modifier) {
        GameCanvas(
            levelData = levelData.value.toList(),
            onSwipe = { dx, dy ->
                val newData = levelData.value.clone()
                if (GameLogic.movePlayer(newData, dx, dy)) {
                    levelData.value = newData
                }
            }
        )
    }
}