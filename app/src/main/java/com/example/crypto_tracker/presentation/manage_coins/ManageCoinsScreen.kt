package com.example.crypto_tracker.presentation.manage_coins

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ShowToast(message: String, duration: Int) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        Toast.makeText(context, message, duration).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCoinsScreen(
    viewModel: ManageCoinsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var newCoinSymbol by remember { mutableStateOf("") }

    state.error?.let { error ->
        ShowToast(error, Toast.LENGTH_LONG)
        LaunchedEffect(error) {
            viewModel.clearError()
        }
    }

    state.message?.let { message ->
        ShowToast(message, Toast.LENGTH_SHORT)
        LaunchedEffect(message) {
            viewModel.clearMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newCoinSymbol,
                onValueChange = { newCoinSymbol = it.uppercase() },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter coin symbol (e.g., BTC)") },
                singleLine = true
            )
            IconButton(
                onClick = {
                    if (newCoinSymbol.isNotBlank()) {
                        viewModel.addCoin(newCoinSymbol)
                        newCoinSymbol = ""
                    }
                }
            ) {
                Icon(Icons.Default.Add, "Add coin")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(state.userCoins) { coin ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(coin.symbol)
                        IconButton(onClick = { viewModel.removeCoin(coin.symbol) }) {
                            Icon(Icons.Default.Delete, "Remove coin")
                        }
                    }
                }
            }
        }
    }
} 