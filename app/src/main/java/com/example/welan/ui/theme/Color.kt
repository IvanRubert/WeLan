package com.example.welan.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val background = Color(0xBA000000)
val cardBackground = Color(0xFF949494)
val textColor = Color(0xFFFFFFFF)
val primaryColor = Color(0xABFC5656)
val secondaryColor = Color(0xAB00C2C2)
val topAppBarPrimary = Color(0x54121212)
val topAppBarSecondary = Color(0xFFB32424)

val apptopBarGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to topAppBarSecondary,
        0.35f to topAppBarSecondary,
        0.95f to topAppBarPrimary,
        1.00f to topAppBarPrimary
    )
)

val appbootomBarGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to topAppBarPrimary,
        0.05f to topAppBarPrimary,
        0.35f to topAppBarSecondary,
        0.75f to topAppBarSecondary,
        1.00f to topAppBarPrimary
    )
)