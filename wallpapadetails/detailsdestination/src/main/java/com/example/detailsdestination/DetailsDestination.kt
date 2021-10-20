package com.example.detailsdestination

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.example.navigator.NavigationDestination

object DetailsDestination: NavigationDestination {

    override fun route(): String = WALLPAPER_DETAIL_BOTTOM_NAV_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(WALLPAPER_ID_PARAM){type =  NavType.StringType})

    const val WALLPAPER_ID_PARAM = "wallpaper"

    private const val WALLPAPER_DETAIL_ROUTE = "wallpaper_detail"
    private const val WALLPAPER_DETAIL_BOTTOM_NAV_ROUTE = "$WALLPAPER_DETAIL_ROUTE/{$WALLPAPER_ID_PARAM}"
    fun createWallPaperDetailsRoute(imageUri: String) = "$WALLPAPER_DETAIL_ROUTE/${imageUri}"
}