package com.mendhie.quicknotes.data.repositories

import com.mendhie.quicknotes.data.models.Note

interface NoteRepository {
    suspend fun createNote(note: Note): Result<Note>
    suspend fun updateNote(note: Note): Result<Note>
    suspend fun getNotes(userId: String): Result<List<Note>>
}