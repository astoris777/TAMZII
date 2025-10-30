package com.example.sokoban

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

object GestureHandler {
    
    fun Modifier.handleSwipeGestures(onSwipe: (Int, Int) -> Unit): Modifier {
        return this.pointerInput(Unit) {
            var totalDrag = Offset.Zero
            var hasMoved = false
            
            detectDragGestures(
                onDragStart = {
                    totalDrag = Offset.Zero
                    hasMoved = false
                },
                onDragEnd = {
                    if (!hasMoved) {
                        processSwipe(totalDrag, onSwipe)
                        hasMoved = true
                    }
                }
            ) { _, dragAmount ->
                totalDrag += dragAmount
            }
        }
    }
    
    private fun processSwipe(totalDrag: Offset, onSwipe: (Int, Int) -> Unit) {
        val threshold = 30f
        
        when {
            abs(totalDrag.x) > abs(totalDrag.y) -> {
                when {
                    totalDrag.x > threshold -> onSwipe(1, 0)
                    totalDrag.x < -threshold -> onSwipe(-1, 0)
                }
            }
            abs(totalDrag.y) > threshold -> {
                when {
                    totalDrag.y > threshold -> onSwipe(0, 1)
                    totalDrag.y < -threshold -> onSwipe(0, -1)
                }
            }
        }
    }
}