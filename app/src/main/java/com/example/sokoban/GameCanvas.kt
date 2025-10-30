package com.example.sokoban

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.sokoban.GameConstants.LEVEL_HEIGHT
import com.example.sokoban.GameConstants.LEVEL_WIDTH
import com.example.sokoban.GestureHandler.handleSwipeGestures
import kotlin.math.roundToInt

@Composable
fun GameCanvas(
    levelData: List<Int>,
    onSwipe: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val tiles = remember { loadGameTiles(context) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .handleSwipeGestures(onSwipe)
    ) {
        val tileSize = calculateTileSize()
        
        for (y in 0 until LEVEL_HEIGHT) {
            for (x in 0 until LEVEL_WIDTH) {
                val tileIndex = levelData[y * LEVEL_WIDTH + x]
                val image = tiles[tileIndex.coerceIn(0, tiles.lastIndex)]

                drawImage(
                    image = image,
                    dstOffset = IntOffset(
                        x = (x * tileSize).roundToInt(),
                        y = (y * tileSize).roundToInt()
                    ),
                    dstSize = IntSize(
                        width = tileSize.roundToInt(),
                        height = tileSize.roundToInt()
                    )
                )
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.calculateTileSize(): Float {
    val tileWidthPx = size.width / LEVEL_WIDTH
    val tileHeightPx = size.height / LEVEL_HEIGHT
    return minOf(tileWidthPx, tileHeightPx)
}

private fun loadGameTiles(context: android.content.Context): Array<ImageBitmap> {
    return arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.empty).asImageBitmap(),
        BitmapFactory.decodeResource(context.resources, R.drawable.wall).asImageBitmap(),
        BitmapFactory.decodeResource(context.resources, R.drawable.box).asImageBitmap(),
        BitmapFactory.decodeResource(context.resources, R.drawable.goal).asImageBitmap(),
        BitmapFactory.decodeResource(context.resources, R.drawable.hero).asImageBitmap(),
        BitmapFactory.decodeResource(context.resources, R.drawable.boxok).asImageBitmap()
    )
}