package com.example.crypto_tracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_coins")
data class UserCoinEntity(
    @PrimaryKey
    val symbol: String
) 