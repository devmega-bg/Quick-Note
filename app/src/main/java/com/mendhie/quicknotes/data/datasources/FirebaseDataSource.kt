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
    suspend fun getUserData(userId: String): Result<User>

    suspend fun createNote(note: Note): Result<Note>
    suspend fun getNotes(userId: String): Result<List<Note>>
}