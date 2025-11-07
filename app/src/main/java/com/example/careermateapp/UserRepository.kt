package com.example.careermateapp


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun getCurrentUser(): User? {
        val currentUser = auth.currentUser ?: return null
        return try {
            val document = firestore.collection("users").document(currentUser.uid).get().await()
            if (document.exists()) {
                User(
                    id = document.id,
                    name = document.getString("name") ?: "",
                    email = document.getString("email") ?: currentUser.email ?: "",
                    profileImage = document.getString("profileImage")
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserProfile(user: User): Boolean {
        return try {
            val userData = hashMapOf(
                "name" to user.name,
                "email" to user.email,
                "profileImage" to user.profileImage
            )
            firestore.collection("users").document(user.id).set(userData).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}