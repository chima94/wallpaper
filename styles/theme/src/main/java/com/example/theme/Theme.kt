package com.example.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.example.color.blue200
import com.example.color.blueDarkPrimary
import com.example.color.yellow200
import com.example.shape.Shapes
import com.example.typography.typography


private val BlueThemeDark = darkColors(
    primary = blue200,
    secondary = yellow200,
    surface = blueDarkPrimary
)

@Composable
fun WallpapersTheme(
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = BlueThemeDark,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}