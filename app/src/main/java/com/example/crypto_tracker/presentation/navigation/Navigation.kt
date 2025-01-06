package com.example.crypto_tracker.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.crypto_tracker.presentation.home.HomeScreen
import com.example.crypto_tracker.presentation.home.HomeViewModel
import com.example.crypto_tracker.presentation.watchlist.EditWatchlistViewModel
import com.example.crypto_tracker.presentation.watchlist.WatchlistViewModel
import com.example.crypto_tracker.presentation.watchlist.WatchlistScreen
import com.example.crypto_tracker.presentation.watchlist.EditWatchlistScreen

@Composable
fun Navigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    watchlistViewModel: WatchlistViewModel,
    editWatchlistViewModel: EditWatchlistViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(homeViewModel)
        }
        composable(Screen.Watchlist.route) {
            WatchlistScreen(
                viewModel = watchlistViewModel,
                onNavigateToEdit = {
                    navController.navigate(Screen.EditWatchlist.route)
                }
            )
        }
        composable(Screen.EditWatchlist.route) {
            EditWatchlistScreen(
                viewModel = editWatchlistViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        Pair(Screen.Home, "Home" to Icons.Default.Home),
        Pair(Screen.Watchlist, "Watchlist" to Icons.Default.Star)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { (screen, titleIcon) ->
            NavigationBarItem(
                icon = { Icon(titleIcon.second, contentDescription = titleIcon.first) },
                label = { Text(titleIcon.first) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
} 