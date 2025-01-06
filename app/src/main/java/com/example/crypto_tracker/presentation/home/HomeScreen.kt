package com.example.crypto_tracker.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crypto_tracker.domain.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crypto Tracker") },
                actions = {
                    IconButton(onClick = { viewModel.refreshCoins() }) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.coins.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No coins available",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(state.coins) { coin ->
                    CoinItem(
                        coin = coin,
                        note = "",
                        onNoteChange = { /* No action */ }
                    )
                }
            }
        }
    }
}

@Composable
fun CoinItem(
    coin: Coin,
    note: String,
    onNoteChange: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf(note) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${coin.name} (${coin.symbol})",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${String.format("%.2f", coin.price)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Text(
                text = "${String.format("%.2f", coin.percentChange24h)}%",
                color = if (coin.percentChange24h >= 0) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error
            )

            if (isEditing) {
                TextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { isEditing = false }) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            onNoteChange(noteText)
                            isEditing = false
                        }
                    ) {
                        Text("Save")
                    }
                }
            } else {
                if (note.isNotEmpty()) {
                    Text(
                        text = note,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                TextButton(
                    onClick = { isEditing = true },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (note.isEmpty()) "Add Note" else "Edit Note")
                }
            }
        }
    }
} 