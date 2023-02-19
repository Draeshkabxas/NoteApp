package com.example.composenoteapp.model

import androidx.room.*
import com.example.composenoteapp.model.Tables.NOTE
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)


    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM $NOTE")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM $NOTE WHERE id=:id ")
    fun getSingleNote(id: Int): Flow<Note>
}