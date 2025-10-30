package com.example.sokoban

import com.example.sokoban.GameConstants.BOX
import com.example.sokoban.GameConstants.BOX_ON_GOAL
import com.example.sokoban.GameConstants.EMPTY
import com.example.sokoban.GameConstants.GOAL
import com.example.sokoban.GameConstants.HERO
import com.example.sokoban.GameConstants.LEVEL_HEIGHT
import com.example.sokoban.GameConstants.LEVEL_WIDTH
import com.example.sokoban.GameConstants.WALL

object GameLogic {
    
    private lateinit var originalGoals: BooleanArray
    
    fun initializeLevel(levelData: IntArray) {
        originalGoals = BooleanArray(levelData.size) { i ->
            levelData[i] == GOAL
        }
    }
    
    fun findPlayerPosition(levelData: IntArray): Pair<Int, Int> {
        for (i in levelData.indices) {
            if (levelData[i] == HERO) {
                return Pair(i % LEVEL_WIDTH, i / LEVEL_WIDTH)
            }
        }
        return Pair(0, 0)
    }

    fun getTile(levelData: IntArray, x: Int, y: Int): Int {
        if (x < 0 || x >= LEVEL_WIDTH || y < 0 || y >= LEVEL_HEIGHT) return WALL
        return levelData[y * LEVEL_WIDTH + x]
    }

    fun setTile(levelData: IntArray, x: Int, y: Int, value: Int) {
        if (x < 0 || x >= LEVEL_WIDTH || y < 0 || y >= LEVEL_HEIGHT) return
        levelData[y * LEVEL_WIDTH + x] = value
    }

    fun movePlayer(levelData: IntArray, dx: Int, dy: Int): Boolean {
        val (playerX, playerY) = findPlayerPosition(levelData)
        val newX = playerX + dx
        val newY = playerY + dy
        
        val targetTile = getTile(levelData, newX, newY)
        
        return when (targetTile) {
            WALL -> false
            
            EMPTY, GOAL -> {
                movePlayerToEmptySpace(levelData, playerX, playerY, newX, newY)
                true
            }
            
            BOX, BOX_ON_GOAL -> {
                tryPushBox(levelData, playerX, playerY, newX, newY, dx, dy)
            }
            
            else -> false
        }
    }
    
    private fun movePlayerToEmptySpace(levelData: IntArray, fromX: Int, fromY: Int, toX: Int, toY: Int) {
        setTile(levelData, fromX, fromY, getTileUnderPlayer(fromX, fromY))
        setTile(levelData, toX, toY, HERO)
    }
    
    private fun getTileUnderPlayer(x: Int, y: Int): Int {
        return if (isOriginalGoal(x, y)) GOAL else EMPTY
    }
    
    private fun isOriginalGoal(x: Int, y: Int): Boolean {
        if (x < 0 || x >= LEVEL_WIDTH || y < 0 || y >= LEVEL_HEIGHT) return false
        return originalGoals[y * LEVEL_WIDTH + x]
    }
    
    private fun tryPushBox(levelData: IntArray, playerX: Int, playerY: Int, boxX: Int, boxY: Int, dx: Int, dy: Int): Boolean {
        val boxNewX = boxX + dx
        val boxNewY = boxY + dy
        val behindBoxTile = getTile(levelData, boxNewX, boxNewY)
        
        if (behindBoxTile == EMPTY || behindBoxTile == GOAL) {
            setTile(levelData, playerX, playerY, getTileUnderPlayer(playerX, playerY))
            
            val tileUnderBox = if (isOriginalGoal(boxX, boxY)) GOAL else EMPTY
            setTile(levelData, boxX, boxY, HERO)
            
            val newBoxValue = if (isOriginalGoal(boxNewX, boxNewY)) BOX_ON_GOAL else BOX
            setTile(levelData, boxNewX, boxNewY, newBoxValue)
            
            return true
        }
        
        return false
    }
}