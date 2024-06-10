package com.petershaan.fungiinfo_mobileapp.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petershaan.fungiinfo_mobileapp.data.repository.ResultRepository
import com.petershaan.fungiinfo_mobileapp.di.Injection
import com.petershaan.fungiinfo_mobileapp.view.analyze.AnalyzeViewModel
import com.petershaan.fungiinfo_mobileapp.view.history.HistoryViewModel

class ViewModelFactory private constructor(
    private val repository: ResultRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AnalyzeViewModel::class.java)) {
            return AnalyzeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }
}