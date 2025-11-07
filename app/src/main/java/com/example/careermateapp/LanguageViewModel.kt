package com.example.careermateapp


import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor() : ViewModel() {

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage

    fun setLanguage(languageCode: String, context: Context) {
        viewModelScope.launch {
            _currentLanguage.value = languageCode

            // Update app locale
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)

            context.resources.updateConfiguration(config, context.resources.displayMetrics)

            // Save preference
            val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("app_language", languageCode).apply()
        }
    }

    fun loadSavedLanguage(context: Context) {
        viewModelScope.launch {
            val sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            val savedLanguage = sharedPreferences.getString("app_language", "en") ?: "en"
            _currentLanguage.value = savedLanguage

            // Apply saved language
            val locale = Locale(savedLanguage)
            Locale.setDefault(locale)

            val config = Configuration()
            config.setLocale(locale)

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}