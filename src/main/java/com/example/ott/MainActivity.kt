package com.example.ott

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ott.screens.ListingScreen
import com.example.ott.screens.SearchScreen
import com.example.ott.ui.theme.OTTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OTTTheme {
                App()
            }
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.CONTENT_LIST_SCREEN) {
        composable(
            route = NavigationKeys.Route.CONTENT_LIST_SCREEN
        ) {
            ListingScreen(navController = navController)
        }
        composable(route = NavigationKeys.Route.SEARCH_SCREEN) {
            SearchScreen(navController = navController)
        }
    }
}

object NavigationKeys {
    object Route {
        const val CONTENT_LIST_SCREEN = "content_list_screen"
        const val SEARCH_SCREEN = "search_screen"
    }

}