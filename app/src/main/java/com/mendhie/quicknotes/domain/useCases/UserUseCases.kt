package com.mendhie.quicknotes.domain.useCases

import com.mendhie.quicknotes.data.models.User
import com.mendhie.quicknotes.data.repositories.UserRepository

class UserUseCases(
    private val userRepository: UserRepository
) {
    suspend fun saveUserData(user: User): Result<Void> {
        return userRepository.saveUserData(user)
    }

}