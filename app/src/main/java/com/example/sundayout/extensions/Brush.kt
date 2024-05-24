package com.example.sundayout.extensions

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Brush.Companion.nativeGradient(): Brush {
    return horizontalGradient(
        listOf(
        Color(red = 91, blue = 255, green = 186),
        Color(red = 148, blue = 226, green = 100),
        Color(red = 230, blue = 157, green = 69),
        Color(red = 253, blue = 77, green = 77),
        Color(red = 253, blue = 77, green = 182),
        Color(red = 253, blue = 77, green = 225)
        )
    )
}