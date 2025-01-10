package com.mendhie.quicknotes.data.repositories

import com.mendhie.quicknotes.data.datasources.FirebaseDataSource
import com.mendhie.quicknotes.data.models.Note

class NoteRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
): NoteRepository {
    override suspend fun createNote(note: Note): Result<Note> {
        return firebaseDataSource.createNote(note)
    }

    override suspend fun updateNote(note: Note): Result<Note> {
        return firebaseDataSource.createNote(note)
    }

    override suspend fun getNotes(userId: String): Result<List<Note>> {
        return firebaseDataSource.getNotes(userId)
    }
}