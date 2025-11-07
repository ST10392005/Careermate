package com.example.careermateapp


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object UploadResume : Screen("upload_resume")
    object JobAnalysis : Screen("job_analysis")
    object ProgressTracker : Screen("progress_tracker")
    object Settings : Screen("settings")
    object LanguageSettings : Screen("language_settings")
}