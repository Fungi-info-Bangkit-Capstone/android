package com.petershaan.fungiinfo_mobileapp.view.analyze

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult
import com.petershaan.fungiinfo_mobileapp.data.repository.ResultRepository
import kotlinx.coroutines.launch

class AnalyzeViewModel(private val repository: ResultRepository): ViewModel()  {
    fun insert(classificationResult: ClassificationResult) {
        viewModelScope.launch { repository.insert(classificationResult) }
    }

    fun delete(classificationResult: ClassificationResult) {
        viewModelScope.launch { repository.delete(classificationResult) }
    }
}