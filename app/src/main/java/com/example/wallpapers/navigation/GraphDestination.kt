package com.example.wallpapers.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.detailsdestination.DetailsDestination
import com.example.detailsui.DetailUI
import com.example.navigator.NavigationDestination
import com.example.searchresultdestination.SearchResultDestination
import com.example.searchresultui.SearchResult

@OptIn(ExperimentalMaterialApi::class)
private val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> = mapOf(
    DetailsDestination to { DetailUI()},
    SearchResultDestination to { SearchResult()}
)

fun NavGraphBuilder.addComposableDestinations() {
    composableDestinations.forEach { entry ->
        val destination = entry.key
        composable(destination.route(), destination.arguments, destination.deepLinks) {
            entry.value()
        }
    }
}