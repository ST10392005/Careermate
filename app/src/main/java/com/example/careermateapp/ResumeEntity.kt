package com.example.careermateapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "resumes")
data class ResumeEntity(
    @PrimaryKey val id: String,
    val fileName: String,
    val filePath: String?,
    val uploadDate: Date,
    val analysisResult: String?,
    val isSynced: Boolean = false
)