package com.example.careermateapp


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(startDestination: String = "splash") {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    // Handle authentication state changes
    LaunchedEffect(currentUser) {
        if (currentUser == null && !isSplashOrAuthRoute(navController.currentDestination?.route)) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Dashboard.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.UploadResume.route) {
            UploadResumeScreen(navController = navController)
        }
        composable(Screen.JobAnalysis.route) {
            JobAnalysisScreen(navController = navController)
        }
        composable(Screen.ProgressTracker.route) {
            ProgressTrackerScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(Screen.LanguageSettings.route) {
            LanguageSettingsScreen(navController = navController)
        }
    }
}

private fun isSplashOrAuthRoute(route: String?): Boolean {
    return route == Screen.Splash.route ||
            route == Screen.Login.route ||
            route == Screen.Register.route
}