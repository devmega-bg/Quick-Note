package com.mendhie.quicknotes.data.datasources

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.mendhie.quicknotes.data.models.Note
import com.mendhie.quicknotes.data.models.User
import kotlinx.coroutines.tasks.await

class FirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): FirebaseDataSource {
    /**
     * Authenticates a user with the provided email and password using Firebase Authentication.
     */
    override suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Saves and retrieve user data to and from Firestore.
     */
    override suspend fun saveUserData(user: User): Result<Void> {
        return try {
            val result = firestore.collection("users").document(user.userId).set(user).await()
            Log.d("Mega", "saveUserData: successful")
            Result.success(result)
        }
        catch (e: Exception){
            Log.d("Mega", "saveUserData: failed")
            Result.failure(e)
        }
    }

    override suspend fun getUserData(userId: String): Result<User> {
        return try {
            val result = firestore.collection("users").document(userId).get().await()
            Result.success(result.toObject(User::class.java)!!)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    /**
     * Saves and retrieves notes from Firestore.
     */
    override suspend fun createNote(note: Note): Result<Note> {
        return try {
            val documentRef = firestore.collection("notes").document()

            // Set the note data (excluding the `id` initially)
            documentRef.set(note).await()

            // Update the note's `id` with the generated document ID
            documentRef.update("id", documentRef.id).await()

            // Return the updated note object
            val updatedNote = note.copy(id = documentRef.id)
            Result.success(updatedNote)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getNotes(userId: String): Result<List<Note>> {
        TODO("Not yet implemented")
    }


}