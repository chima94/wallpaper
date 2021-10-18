package com.example.color


import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val blue200 = Color(0xff91a4fc)
val blue700 = Color(0xff0336ff)
val blue800 = Color(0xff0035c9)
val blueDarkPrimary = Color(0xff1c1d24)

val pink200 = Color(0xffff7597)
val pink500 = Color(0xffff0266)
val pink600 = Color(0xffd8004d)
val pinkDarkPrimary = Color(0xff24191c)

val yellow200 = Color(0xffffeb46)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}