package com.mendhie.quicknotes.data.datasources

import com.google.firebase.auth.FirebaseUser
import com.mendhie.quicknotes.data.models.Note
import com.mendhie.quicknotes.data.models.User

interface FirebaseDataSource {
    suspend fun signIn(email: String, password: String): Result<FirebaseUser>
    suspend fun signUp(email: String, password: String): Result<FirebaseUser>
    suspend fun signOut()
    fun getCurrentUser(): FirebaseUser?

    suspend fun saveUserData(user: User): Result<Void>
    fun getUserData(userId: String, onResult: (Result<User>) -> Unit)

    suspend fun createNote(note: Note): Result<Note>
    fun getNotes(userId: String, onResult: (Result<List<Note>>) -> Unit)
}