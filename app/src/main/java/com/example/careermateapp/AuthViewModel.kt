package com.example.careermateapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            _currentUser.value = userRepository.getCurrentUser()
        }
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        _isLoading.value = true
        _errorMessage.value = null

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    loadCurrentUser()
                    onSuccess()
                } else {
                    _errorMessage.value = task.exception?.message ?: "Sign in failed"
                }
            }
    }

    fun signUp(name: String, email: String, password: String, onSuccess: () -> Unit) {
        _isLoading.value = true
        _errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(
                        id = auth.currentUser?.uid ?: "",
                        name = name,
                        email = email
                    )
                    viewModelScope.launch {
                        val success = userRepository.updateUserProfile(user)
                        _isLoading.value = false
                        if (success) {
                            _currentUser.value = user
                            onSuccess()
                        } else {
                            _errorMessage.value = "Failed to create user profile"
                        }
                    }
                } else {
                    _isLoading.value = false
                    _errorMessage.value = task.exception?.message ?: "Sign up failed"
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }
}