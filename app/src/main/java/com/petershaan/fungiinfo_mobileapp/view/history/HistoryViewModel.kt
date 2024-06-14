package com.petershaan.fungiinfo_mobileapp.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult
import com.petershaan.fungiinfo_mobileapp.data.repository.ResultRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: ResultRepository) : ViewModel() {
    private val _results = MutableLiveData<List<ClassificationResult>>()
    val results: LiveData<List<ClassificationResult>> = _results

    fun loadResultsForUser(userId: String) {
        viewModelScope.launch {
            repository.getAllResultsForUser(userId).observeForever {
                _results.postValue(it)
            }
        }
    }
}