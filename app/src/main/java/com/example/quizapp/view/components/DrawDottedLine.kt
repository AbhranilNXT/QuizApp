package com.example.quizapp.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun DrawDottedLine(pathEffect : androidx.compose.ui.graphics.PathEffect) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(color = AppColors._LightGrey,
            start = Offset(0f,0f),
            end = Offset(size.width,0f),
            pathEffect = pathEffect)

    }
}