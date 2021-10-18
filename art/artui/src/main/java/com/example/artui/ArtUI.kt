package com.example.artui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.topappbar.WallpaperTopAppBar

@Composable
fun ArtUI() {

    Scaffold(
        topBar = { WallpaperTopAppBar(text = "Art") }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Art",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}