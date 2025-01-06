package com.example.crypto_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.crypto_tracker.presentation.home.HomeViewModel
import com.example.crypto_tracker.presentation.navigation.BottomNavBar
import com.example.crypto_tracker.presentation.navigation.Navigation
import com.example.crypto_tracker.ui.theme.CryptoTrackerTheme
import com.example.crypto_tracker.presentation.watchlist.EditWatchlistViewModel
import com.example.crypto_tracker.presentation.watchlist.WatchlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val watchlistViewModel: WatchlistViewModel by viewModels()
    private val editWatchlistViewModel: EditWatchlistViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoTrackerTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = { 
                        BottomNavBar(navController = navController)
                    }
                ) { padding ->
                    Navigation(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        watchlistViewModel = watchlistViewModel,
                        editWatchlistViewModel = editWatchlistViewModel,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}