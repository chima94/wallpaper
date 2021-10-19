package com.example.natureui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.image.ImageUI
import com.example.topappbar.WallpaperTopAppBar

@Composable
fun NatureUI(){
    val size = Size(200.dp.value, 200.dp.value)
    Scaffold(
        topBar = { WallpaperTopAppBar(text = "Nature") }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //ImageUI()
        }
    }
}