package com.example.sokoban

object GameConstants {
    const val LEVEL_WIDTH = 10
    const val LEVEL_HEIGHT = 10
    
    const val EMPTY = 0
    const val WALL = 1
    const val BOX = 2
    const val GOAL = 3
    const val HERO = 4
    const val BOX_ON_GOAL = 5
    
    val INITIAL_LEVEL_DATA = intArrayOf(
        1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        1, 0, 0, 0, 0, 0, 0, 0, 1, 0,
        1, 0, 2, 3, 3, 2, 1, 0, 1, 0,
        1, 0, 1, 3, 2, 3, 2, 0, 1, 0,
        1, 0, 1, 3, 2, 3, 2, 0, 1, 0,
        1, 0, 2, 3, 3, 2, 4, 0, 1, 0,
        1, 0, 1, 3, 2, 3, 2, 0, 1, 0,
        1, 0, 2, 3, 3, 2, 1, 0, 1, 0,
        1, 0, 0, 0, 0, 0, 0, 0, 1, 0,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
}