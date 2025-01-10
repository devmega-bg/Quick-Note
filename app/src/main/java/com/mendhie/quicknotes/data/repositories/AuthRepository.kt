package com.mendhie.quicknotes.data.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<FirebaseUser>
    suspend fun signUp(email: String, password: String): Result<FirebaseUser>
    suspend fun signOut()
    fun getCurrentUser(): FirebaseUser?
}