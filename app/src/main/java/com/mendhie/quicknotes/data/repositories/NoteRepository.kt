package com.mendhie.quicknotes.data.repositories

import com.mendhie.quicknotes.data.models.Note

interface NoteRepository {
    suspend fun createNote(note: Note): Result<Note>
    suspend fun updateNote(note: Note): Result<Note>
    fun getNotes(userId: String, onResult: (Result<List<Note>>) -> Unit)
}