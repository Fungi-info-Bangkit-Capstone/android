package com.petershaan.fungiinfo_mobileapp.view.main

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {
    lateinit var auth: FirebaseAuth
    var currentPosition: Int = 0

    fun isUserLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return  currentUser != null
    }
}
