package com.example.careermateapp


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
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