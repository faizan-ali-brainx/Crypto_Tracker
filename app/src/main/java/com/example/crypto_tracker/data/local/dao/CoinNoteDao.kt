package com.example.crypto_tracker.data.local.dao

import androidx.room.*
import com.example.crypto_tracker.data.local.entity.CoinNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinNoteDao {
    @Query("SELECT * FROM coin_notes")
    fun getAllNotes(): Flow<List<CoinNoteEntity>>

    @Query("SELECT * FROM coin_notes WHERE coinId = :coinId")
    suspend fun getNoteById(coinId: Int): CoinNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: CoinNoteEntity)

    @Delete
    suspend fun deleteNote(note: CoinNoteEntity)
} 