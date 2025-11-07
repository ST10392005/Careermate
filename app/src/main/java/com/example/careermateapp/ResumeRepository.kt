package com.example.careermateapp

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResumeRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getAllResumes(): Flow<List<ResumeEntity>> {
        return database.resumeDao().getAllResumes()
    }

    suspend fun insertResume(resume: ResumeEntity) {
        database.resumeDao().insertResume(resume)
    }

    suspend fun updateResume(resume: ResumeEntity) {
        database.resumeDao().updateResume(resume)
    }

    suspend fun getUnsyncedResumes(): List<ResumeEntity> {
        return database.resumeDao().getUnsyncedResumes()
    }
}