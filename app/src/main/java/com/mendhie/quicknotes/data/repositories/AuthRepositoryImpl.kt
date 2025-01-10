package com.mendhie.quicknotes.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.mendhie.quicknotes.data.datasources.FirebaseDataSource

class AuthRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : AuthRepository{
    override suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return firebaseDataSource.signIn(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<FirebaseUser> {
        return firebaseDataSource.signUp(email, password)
    }

    override suspend fun signOut() {
        return firebaseDataSource.signOut()
    }

    override fun getCurrentUser(): FirebaseUser?{
        return firebaseDataSource.getCurrentUser()
    }

}