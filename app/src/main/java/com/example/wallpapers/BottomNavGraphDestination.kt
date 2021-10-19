package com.example.wallpapers

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.artui.ArtUI
import com.example.bottomnavigation.*
import com.example.natureui.NatureUI
import com.example.religionui.ReligionUI
import com.example.searchui.SearchUI

private val destinationsBottomNav: Map<BottomNavigationEntry, @Composable () -> Unit> = mapOf(
    ReligionRoute to { ReligionUI()},
    NatureRoute to { NatureUI()},
    ArtRoute to { ArtUI()},
    SearchRoute to { SearchUI()}
)



fun NavGraphBuilder.addBottomNavigationDestinations(){
    destinationsBottomNav.forEach{ entry ->
        val destination = entry.key
        composable(destination.route){
            entry.value()
        }
    }
}