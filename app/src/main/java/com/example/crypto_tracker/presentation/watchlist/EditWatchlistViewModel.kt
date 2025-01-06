package com.example.crypto_tracker.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker.data.local.entity.UserCoinEntity
import com.example.crypto_tracker.domain.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWatchlistViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditWatchlistState())
    val state = _state.asStateFlow()

    init {
        refreshCoins()
    }

    fun refreshCoins() {
        viewModelScope.launch {
            val userCoins = repository.getUserCoins().first()
            _state.update { it.copy(userCoins = userCoins) }
        }
    }

    fun addCoin(symbol: String) {
        if (symbol.isBlank()) {
            _state.update { it.copy(error = "Please enter a coin symbol") }
            return
        }

        // Basic validation for coin symbol format
        if (!symbol.matches("[A-Z0-9]+".toRegex())) {
            _state.update { it.copy(error = "Invalid coin symbol format") }
            return
        }

        viewModelScope.launch {
            try {
                repository.addUserCoin(symbol)
                refreshCoins()
                _state.update { it.copy(error = null, message = "Added $symbol to watchlist") }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error: ${e.message}") }
            }
        }
    }

    fun removeCoin(symbol: String) {
        viewModelScope.launch {
            try {
                repository.removeUserCoin(symbol)
                refreshCoins()
                _state.update { it.copy(message = "Successfully removed $symbol") }
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error removing coin: ${e.message}") }
            }
        }
    }
}

data class EditWatchlistState(
    val userCoins: List<UserCoinEntity> = emptyList(),
    val error: String? = null,
    val message: String? = null
) 