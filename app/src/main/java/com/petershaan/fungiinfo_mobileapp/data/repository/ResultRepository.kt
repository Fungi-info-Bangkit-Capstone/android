package com.petershaan.fungiinfo_mobileapp.data.repository

import androidx.lifecycle.LiveData
import com.petershaan.fungiinfo_mobileapp.data.local.AppDao
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult

class ResultRepository(private val dao: AppDao) {
    fun getAll(): LiveData<List<ClassificationResult>> {
        return dao.getAll()
    }

    fun getById(id: Int): LiveData<ClassificationResult> {
        return dao.getClassificationResultById(id)
    }

    suspend fun insert(classificationResult: ClassificationResult) {
        dao.insert(classificationResult)
    }

    suspend fun delete(classificationResult: ClassificationResult) {
        dao.delete(classificationResult)
    }

    companion object {
        @Volatile
        private var instance: ResultRepository? = null

        fun getInstance(
            dao: AppDao,
        ): ResultRepository =
            instance ?: synchronized(this) {
                instance ?: ResultRepository(dao)
            }.also { instance = it }
    }
}