package com.mendhie.quicknotes.data.repositories

import com.mendhie.quicknotes.data.datasources.FirebaseDataSource
import com.mendhie.quicknotes.data.models.User

class UserRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
): UserRepository {
    override suspend fun saveUserData(user: User): Result<Void> {
        return firebaseDataSource.saveUserData(user)
    }

    override fun getUserData(userId: String, onResult: (Result<User>) -> Unit) {
        firebaseDataSource.getUserData(userId, onResult)
    }
}