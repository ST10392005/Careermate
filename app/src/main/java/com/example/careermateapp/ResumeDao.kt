package com.example.careermateapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ResumeDao {
    @Query("SELECT * FROM resumes ORDER BY uploadDate DESC")
    fun getAllResumes(): Flow<List<ResumeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResume(resume: ResumeEntity)

    @Update
    suspend fun updateResume(resume: ResumeEntity)

    @Query("SELECT * FROM resumes WHERE isSynced = 0")
    suspend fun getUnsyncedResumes(): List<ResumeEntity>

    @Query("DELETE FROM resumes WHERE id = :id")
    suspend fun deleteResume(id: String)
}