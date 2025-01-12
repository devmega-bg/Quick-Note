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

    override fun getNotes(userId: String, onResult: (Result<List<Note>>) -> Unit){
        firebaseDataSource.getNotes(userId, onResult)
    }
}