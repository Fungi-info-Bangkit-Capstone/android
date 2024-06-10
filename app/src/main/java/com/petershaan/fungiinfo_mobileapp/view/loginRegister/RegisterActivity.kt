package com.petershaan.fungiinfo_mobileapp.view.loginRegister

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityLoginBinding
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityRegisterBinding
import com.petershaan.fungiinfo_mobileapp.view.HomeActivity
import com.petershaan.fungiinfo_mobileapp.view.SplashActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var errorIcon: Drawable
    private lateinit var validIcon: Drawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        errorIcon = ContextCompat.getDrawable(this, R.drawable.error)!!
        validIcon = ContextCompat.getDrawable(this, R.drawable.success)!!

        binding.root.setOnTouchListener { _, _ ->
            clearFocusFromEditText()
            hideKeyboard()
            true
        }

        binding.btnGoogle.setOnClickListener{
            googleSignOut()
        }

        binding.punyaAkun.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.etEmail.addTextChangedListener(emailTextWatcher)
        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.btnAksi.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val displayName = binding.etDisplayName.text.toString().trim()
            var isValid = true

            if (email.isEmpty()) {
                binding.emailError.text = "Email harus diisi"
                binding.emailInputLayout.setEndIconDrawable(errorIcon)
                binding.emailError.visibility = View.VISIBLE
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailError.text = "Email tidak valid"
                binding.emailError.visibility = View.VISIBLE
                binding.emailInputLayout.setEndIconDrawable(errorIcon)
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)

                isValid = false
            } else {
                binding.emailError.visibility = View.GONE
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.green)
                binding.emailInputLayout.setEndIconDrawable(validIcon)
                binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)))
            }

            if (password.isEmpty() || password.length < 6) {
                binding.passwordError.text = "Password harus lebih dari 6 karakter"
                binding.passwordError.visibility = View.VISIBLE
                binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
                isValid = false
            } else {
                binding.passwordError.visibility = View.GONE
            }

            if (displayName.isEmpty()) {
                binding.displayNameError.text = "Display Name harus diisi"
                binding.displayNameError.visibility = View.VISIBLE
                binding.namaInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
                isValid = false
            } else if (displayName.length > 13) {
                binding.displayNameError.text = "Nama tidak boleh lebih dari 13 huruf"
                binding.displayNameError.visibility = View.VISIBLE
                binding.etDisplayName.requestFocus()
                binding.namaInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
                isValid = false
            }
            else {
                binding.displayNameError.visibility = View.GONE
                binding.namaInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.green)
            }

            if (isValid) {
                registerUser(email, password, displayName)
            }
        }

        updateButtonState()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun googleSignOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent,RC_SIGN_IN)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sign out failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun clearFocusFromEditText() {
        binding.etEmail.clearFocus()
        binding.etPassword.clearFocus()
        binding.etDisplayName.clearFocus()
    }



    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.etEmail.text.toString().trim()
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailInputLayout.setEndIconDrawable(errorIcon)
                binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@RegisterActivity, R.color.red)))
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
            } else {
                binding.emailInputLayout.setEndIconDrawable(validIcon)
                binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@RegisterActivity, R.color.green)))
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.green)
            }
            updateButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val password = binding.etPassword.text.toString().trim()
            if (password.isEmpty() || password.length < 6) {
                binding.passwordError.text = "Password harus lebih dari 6 karakter"
                binding.passwordError.visibility = View.VISIBLE
                binding.passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@RegisterActivity, R.color.white)))
                binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.red)
            } else {
                binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this@RegisterActivity, R.color.green)
                binding.passwordError.visibility = View.GONE
                binding.passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@RegisterActivity, R.color.white)))
            }
            updateButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val displayName = binding.etDisplayName.text.toString().trim()

        val isEnabled = email.isNotEmpty() && password.isNotEmpty() && displayName.isNotEmpty()
        binding.btnAksi.alpha = if (isEnabled) 1.0f else 0.5f
        binding.btnAksi.isClickable = isEnabled
        binding.btnAksi.isFocusable = isEnabled
    }

    private fun registerUser(email: String, password: String, displayName: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registration successful. Please login.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        } else {
                            Toast.makeText(this, "Failed to update profile: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    }
    companion object {
        private const val RC_SIGN_IN = 9001
    }

}