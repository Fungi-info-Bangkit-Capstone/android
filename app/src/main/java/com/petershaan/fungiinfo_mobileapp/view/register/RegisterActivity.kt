package com.petershaan.fungiinfo_mobileapp.view.register

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityRegisterBinding
import com.petershaan.fungiinfo_mobileapp.view.SplashActivity
import com.petershaan.fungiinfo_mobileapp.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var errorIcon: Drawable
    private lateinit var validIcon: Drawable
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener{
            finish()
        }

        errorIcon = ContextCompat.getDrawable(this, R.drawable.ic_error)!!
        validIcon = ContextCompat.getDrawable(this, R.drawable.ic_success)!!

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnGoogle.setOnClickListener {
            registerViewModel.googleSignOut(this)
        }

        binding.punyaAkun.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.etEmail.addTextChangedListener(emailTextWatcher)
        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.etDisplayName.addTextChangedListener(displayNameTextWatcher)
        binding.btnAksi.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val displayName = binding.etDisplayName.text.toString().trim()
            registerViewModel.registerUser(this, email, password, displayName)
        }

        updateButtonState()
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.etEmail.text.toString().trim()
            registerViewModel.validateEmail(email)
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val password = binding.etPassword.text.toString().trim()
            registerViewModel.validatePassword(password)
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val displayNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val displayName = binding.etDisplayName.text.toString().trim()
            registerViewModel.validateDisplayName(displayName)
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun observeViewModel() {
        registerViewModel.emailError.observe(this, Observer { error ->
            binding.emailError.text = error
            binding.emailError.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
            setEmailInputState(error.isEmpty())
        })

        registerViewModel.passwordError.observe(this, Observer { error ->
            binding.passwordError.text = error
            binding.passwordError.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
            setPasswordInputState(error.isEmpty())
        })

        registerViewModel.displayNameError.observe(this, Observer { error ->
            binding.displayNameError.text = error
            binding.displayNameError.visibility = if (error.isEmpty()) View.GONE else View.VISIBLE
            setDisplayNameInputState(error.isEmpty())
        })

        registerViewModel.registerResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Registration successful. Please login.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })

        registerViewModel.googleSignInResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Signed in with Google", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SplashActivity::class.java))
            } else {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setEmailInputState(isValid: Boolean) {
        if (isValid) {
            binding.emailInputLayout.setEndIconDrawable(validIcon)
            binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)))
            binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.green)
        } else {
            binding.emailInputLayout.setEndIconDrawable(errorIcon)
            binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)))
            binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.red)
        }
    }

    private fun setPasswordInputState(isValid: Boolean) {
        if (isValid) {
            binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.green)
            binding.passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        } else {
            binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.red)
            binding.passwordInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)))
        }
    }

    private fun setDisplayNameInputState(isValid: Boolean) {
        if (isValid) {
            binding.namaInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.green)
        } else {
            binding.namaInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.red)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RegisterViewModel.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                registerViewModel.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
