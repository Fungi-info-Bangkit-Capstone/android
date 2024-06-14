package com.petershaan.fungiinfo_mobileapp.view.register

import android.content.Context
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.petershaan.fungiinfo_mobileapp.R

class RegisterViewModel : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _googleSignInResult = MutableLiveData<Boolean>()
    val googleSignInResult: LiveData<Boolean> = _googleSignInResult

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String> = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _displayNameError = MutableLiveData<String>()
    val displayNameError: LiveData<String> = _displayNameError

    private val _emailValid = MutableLiveData<Boolean>()
    val emailValid: LiveData<Boolean> = _emailValid

    private val _passwordValid = MutableLiveData<Boolean>()
    val passwordValid: LiveData<Boolean> = _passwordValid

    private val _displayNameValid = MutableLiveData<Boolean>()
    val displayNameValid: LiveData<Boolean> = _displayNameValid

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun validateEmail(email: String) {
        when {
            email.isEmpty() -> {
                _emailError.value = "Email harus diisi"
                _emailValid.value = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _emailError.value = "Email tidak valid"
                _emailValid.value = false
            }
            else -> {
                _emailError.value = ""
                _emailValid.value = true
            }
        }
    }

    fun validatePassword(password: String) {
        when {
            password.isEmpty() || password.length < 6 -> {
                _passwordError.value = "Password harus lebih dari 6 karakter"
                _passwordValid.value = false
            }
            else -> {
                _passwordError.value = ""
                _passwordValid.value = true
            }
        }
    }

    fun validateDisplayName(displayName: String) {
        when {
            displayName.isEmpty() -> {
                _displayNameError.value = "Display Name harus diisi"
                _displayNameValid.value = false
            }
            displayName.length > 13 -> {
                _displayNameError.value = "Nama tidak boleh lebih dari 13 huruf"
                _displayNameValid.value = false
            }
            else -> {
                _displayNameError.value = ""
                _displayNameValid.value = true
            }
        }
    }

    fun registerUser(context: Context, email: String, password: String, displayName: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            _registerResult.value = true
                        } else {
                            _registerResult.value = false
                        }
                    }
                } else {
                    _registerResult.value = false
                }
            }
    }

    fun googleSignOut(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
            .addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                (context as AppCompatActivity).startActivityForResult(signInIntent, RC_SIGN_IN)
            }
            .addOnFailureListener {
                _googleSignInResult.value = false
            }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _googleSignInResult.value = task.isSuccessful
            }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}
