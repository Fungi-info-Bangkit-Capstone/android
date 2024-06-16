package com.petershaan.fungiinfo_mobileapp.view.history

import androidx.lifecycle.LifecycleOwner
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadResultsForUser(userId: String, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            _isLoading.postValue(true)

            repository.getAllResultsForUser(userId).observe(lifecycleOwner) { results ->
                _results.value = results
                _isLoading.postValue(false)
            }
        }
    }
}
