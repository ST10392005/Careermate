package com.example.careermateapp


data class JobAnalysis(
    val skills: List<String> = emptyList(),
    val experience: String = "",
    val responsibilities: List<String> = emptyList(),
    val interviewTips: List<String> = emptyList(),
    val matchPercentage: Int = 0
)