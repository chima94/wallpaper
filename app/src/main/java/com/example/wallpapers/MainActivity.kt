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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.ReligionRoute
import com.example.bottomnavigation.WallpaperBottomNavigation
import com.example.navigator.Navigator
import com.example.theme.WallpapersTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
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



@Composable
fun WallPaperScaffold(
    navigator: Navigator
){

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            WallpaperBottomNavigation(navController = navController)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = ReligionRoute.route,
            builder = {
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