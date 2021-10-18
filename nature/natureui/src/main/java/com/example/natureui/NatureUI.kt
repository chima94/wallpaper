package com.example.natureui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.topappbar.WallpaperTopAppBar

@Composable
fun NatureUI(){

    Scaffold(
        topBar = { WallpaperTopAppBar(text = "Nature") }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Nature",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}