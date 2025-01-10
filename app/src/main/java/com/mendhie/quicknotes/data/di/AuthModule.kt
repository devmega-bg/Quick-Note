package com.mendhie.quicknotes.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.mendhie.quicknotes.data.datasources.FirebaseDataSource
import com.mendhie.quicknotes.data.datasources.FirebaseDataSourceImpl
import com.mendhie.quicknotes.data.repositories.AuthRepository
import com.mendhie.quicknotes.data.repositories.AuthRepositoryImpl
import com.mendhie.quicknotes.data.repositories.NoteRepository
import com.mendhie.quicknotes.data.repositories.NoteRepositoryImpl
import com.mendhie.quicknotes.data.repositories.UserRepository
import com.mendhie.quicknotes.data.repositories.UserRepositoryImpl
import com.mendhie.quicknotes.domain.useCases.AuthenticationUseCases
import com.mendhie.quicknotes.domain.useCases.NoteUseCases
import com.mendhie.quicknotes.domain.useCases.UserUseCases

class AuthModule{

    companion object {
        private val firebaseDataSource: FirebaseDataSource = FirebaseDataSourceImpl(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance()
        )

        private val firebaseAuthRepo: AuthRepository = AuthRepositoryImpl(firebaseDataSource)
        private val userRepository: UserRepository = UserRepositoryImpl(firebaseDataSource)
        private val noteRepository: NoteRepository = NoteRepositoryImpl(firebaseDataSource)

        val authenticationUseCases: AuthenticationUseCases = AuthenticationUseCases(firebaseAuthRepo)
        val userUseCases: UserUseCases = UserUseCases(userRepository)
        val noteUseCases: NoteUseCases = NoteUseCases(noteRepository)
    }
}