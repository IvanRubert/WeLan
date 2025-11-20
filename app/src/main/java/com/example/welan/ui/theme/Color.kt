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

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val appBarGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.00f to topAppBarSecondary,
        0.70f to topAppBarSecondary,
        1.00f to topAppBarPrimary
    )
)