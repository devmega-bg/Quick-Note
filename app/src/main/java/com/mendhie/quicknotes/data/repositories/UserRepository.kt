package com.mendhie.quicknotes.data.repositories

import com.mendhie.quicknotes.data.models.User

interface UserRepository {
    suspend fun saveUserData(user: User): Result<Void>
    fun getUserData(userId: String, onResult: (Result<User>) -> Unit)
}