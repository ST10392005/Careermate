package com.example.careermateapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository
) : ViewModel() {

    val resumes = resumeRepository.getAllResumes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun uploadResume(fileName: String, filePath: String?) {
        viewModelScope.launch {
            val resume = ResumeEntity(
                id = UUID.randomUUID().toString(),
                fileName = fileName,
                filePath = filePath,
                uploadDate = Date(),
                analysisResult = null,
                isSynced = false
            )
            resumeRepository.insertResume(resume)
        }
    }

    fun syncResumes() {
        viewModelScope.launch {
            val unsyncedResumes = resumeRepository.getUnsyncedResumes()
            // TODO: Implement server sync logic
            unsyncedResumes.forEach { resume ->
                resumeRepository.updateResume(resume.copy(isSynced = true))
            }
        }
    }
}