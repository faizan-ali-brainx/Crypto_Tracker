package com.example.crypto_tracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypto_tracker.domain.model.Coin
import com.example.crypto_tracker.domain.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        refreshCoins()
    }

    fun refreshCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val allCoins = repository.getTopCoins()
                _state.update { it.copy(coins = allCoins, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun updateNote(coinId: Int, note: String) {
        viewModelScope.launch {
            repository.updateCoinNote(coinId, note)
        }
    }
}

data class HomeState(
    val coins: List<Coin> = emptyList(),
    val coinNotes: Map<Int, String> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
) 