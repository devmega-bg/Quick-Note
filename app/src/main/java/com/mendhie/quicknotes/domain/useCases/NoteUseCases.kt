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

    fun getNotes(userId: String, onResult: (Result<List<Note>>) -> Unit){
        return noteRepository.getNotes(userId, onResult)
    }
}