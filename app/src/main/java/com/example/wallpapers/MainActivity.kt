package com.example.wallpapers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.ReligionRoute
import com.example.bottomnavigation.WallpaperBottomNavigation
import com.example.detailsdestination.DetailsDestination
import com.example.navigator.Navigator
import com.example.navigator.NavigatorEvent
import com.example.theme.WallpapersTheme
import com.example.wallpapers.navigation.addComposableDestinations
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WallpapersTheme{
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Surface(color = MaterialTheme.colors.background) {
                        WallPaperScaffold(navigator = navigator)
                    }
                }

            }
        }
    }
}




private val hideBottomNavFromDestinationRoutes = listOf(
    DetailsDestination.route()
)

@Composable
fun WallPaperScaffold(
    navigator: Navigator
){

    val navController = rememberNavController()
    LaunchedEffect(navController) {
        navigator.destination.collect {
            when (val event = it) {
                is NavigatorEvent.NavigateUp -> navController.navigateUp()
                is NavigatorEvent.Directions -> navController.navigate(
                    event.destination,
                    event.builder
                )
            }
        }
    }

    Scaffold(
        bottomBar = {
            WallpaperBottomNavigation(navController = navController, hideBottomNavFromDestinationRoutes)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = ReligionRoute.route,
            builder = {
                addComposableDestinations()
                addBottomNavigationDestinations()
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WallpapersTheme {

    }
}