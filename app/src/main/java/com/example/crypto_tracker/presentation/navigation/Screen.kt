package com.example.crypto_tracker.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Watchlist : Screen("watchlist")
    object EditWatchlist : Screen("edit_watchlist")
} 