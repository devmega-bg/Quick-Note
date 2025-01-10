package com.mendhie.quicknotes.domain.useCases

import com.mendhie.quicknotes.data.models.Note
import com.mendhie.quicknotes.data.repositories.NoteRepository

class NoteUseCases(private val noteRepository: NoteRepository) {

    suspend fun createNote(note: Note): Result<Note> {
        return noteRepository.createNote(note)
    }

    suspend fun updateNote(note: Note): Result<Note> {
        return noteRepository.createNote(note)
    }

    suspend fun getNotes(userId: String): Result<List<Note>> {
        return noteRepository.getNotes(userId)
    }
}