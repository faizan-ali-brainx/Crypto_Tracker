package com.example.crypto_tracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_notes")
data class CoinNoteEntity(
    @PrimaryKey
    val coinId: Int,
    val note: String
) 