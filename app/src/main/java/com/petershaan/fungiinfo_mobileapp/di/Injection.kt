package com.petershaan.fungiinfo_mobileapp.di

import android.content.Context
import com.petershaan.fungiinfo_mobileapp.data.local.AppDatabase
import com.petershaan.fungiinfo_mobileapp.data.repository.ResultRepository

object Injection {
    fun provideRepository(context: Context): ResultRepository {
        val database = AppDatabase.getDatabase(context)
        val dao = database.appDao()
        return ResultRepository.getInstance(dao)
    }
}