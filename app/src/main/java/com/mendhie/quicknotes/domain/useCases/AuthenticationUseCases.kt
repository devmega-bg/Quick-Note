package com.mendhie.quicknotes.domain.useCases

import com.google.firebase.auth.FirebaseUser
import com.mendhie.quicknotes.data.repositories.AuthRepository

class AuthenticationUseCases(
    private val authRepository: AuthRepository
) {
    suspend fun signIn(email: String, password: String): Result<FirebaseUser>{
        return authRepository.login(email, password)
    }

    suspend fun signUp(email: String, password: String): Result<FirebaseUser>{
        return authRepository.signUp(email, password)
    }

    suspend fun signOut(){
        authRepository.signOut()
    }

    fun getCurrentUser(): FirebaseUser?{
        return authRepository.getCurrentUser()
    }
}