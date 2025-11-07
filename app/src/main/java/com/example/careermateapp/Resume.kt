package com.example.careermateapp

import java.util.Date

data class Resume(
    val id: String = "",
    val fileName: String = "",
    val fileUrl: String? = null,
    val uploadDate: Date = Date(),
    val analysisResult: String? = null,
    val isSynced: Boolean = false
)