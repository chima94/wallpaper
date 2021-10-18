package com.example.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*


object ReligionRoute: BottomNavigationEntry(RELIGION, com.example.strings.R.string.religion)
object NatureRoute: BottomNavigationEntry(NATURE, com.example.strings.R.string.nature)
object ArtRoute: BottomNavigationEntry(ART, com.example.strings.R.string.art)
object SearchRoute: BottomNavigationEntry(SEARCH, com.example.strings.R.string.search)

val bottomNavigationEntries = listOf(
    BottomNavigationUiEntry(
        ReligionRoute,
        Icons.Filled.AllInclusive
    ),

    BottomNavigationUiEntry(
        NatureRoute,
        Icons.Filled.Nature
    ),

    BottomNavigationUiEntry(
        ArtRoute,
        Icons.Filled.Wallpaper
    ),

    BottomNavigationUiEntry(
        SearchRoute,
        Icons.Filled.Search
    )
)