package com.petershaan.fungiinfo_mobileapp.view.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityLoginBinding
import com.petershaan.fungiinfo_mobileapp.view.SplashActivity
import com.petershaan.fungiinfo_mobileapp.view.lupa.LupaPasswordActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var errorIcon: Drawable
    private lateinit var validIcon: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener{
            finish()
        }

        initIcons()
        setupListeners()
        observeViewModel()
    }

    private fun initIcons() {
        errorIcon = ContextCompat.getDrawable(this, R.drawable.ic_error)!!
        validIcon = ContextCompat.getDrawable(this, R.drawable.ic_success)!!
    }

    private fun setupListeners() {
        binding.btnGoogle.setOnClickListener { googleSignOut() }

        binding.etEmail.addTextChangedListener(emailTextWatcher)
        binding.etPassword.addTextChangedListener(passwordTextWatcher)

        binding.lupaPassword.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivity::class.java))
        }

        binding.btnAksi.setOnClickListener { handleLogin() }

        updateButtonState()
    }

    private fun handleLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (isValidEmail(email) && isValidPassword(password)) {
            loginViewModel.loginUser(email, password)
        } else {
            showValidationError(email, password)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    private fun showValidationError(email: String, password: String) {
        if (email.isEmpty() || !isValidEmail(email)) {
            binding.emailError.text = getString(R.string.email_tidak_valid)
            binding.emailError.visibility = View.VISIBLE
            setEmailErrorState()
        } else {
            binding.emailError.visibility = View.GONE
        }

        if (password.isEmpty() || !isValidPassword(password)) {
            binding.passwordError.text = getString(R.string.password_harus_lebih_dari_6_karakter)
            binding.passwordError.visibility = View.VISIBLE
            setPasswordErrorState()
        } else {
            binding.passwordError.visibility = View.GONE
        }
    }

    private fun setEmailErrorState() {
        binding.emailInputLayout.apply {
            setEndIconDrawable(errorIcon)
            boxStrokeColor = ContextCompat.getColor(this@LoginActivity, R.color.red)
            setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@LoginActivity, R.color.red)))
        }
    }

    private fun setPasswordErrorState() {
        binding.passwordInputLayout.apply {
            boxStrokeColor = ContextCompat.getColor(this@LoginActivity, R.color.red)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                loginViewModel.firebaseAuthWithGoogle(account.idToken!!)
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
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sign out failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun observeViewModel() {
        loginViewModel.loginResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                onLoginSuccess()
            } else {
                onLoginFailed()
            }
        })

        loginViewModel.googleSignInResult.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                onGoogleSignInSuccess()
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onLoginSuccess() {
        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
        Intent(this@LoginActivity, SplashActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun onLoginFailed() {
        binding.passwordError.text = getString(R.string.email_atau_password_salah)
        binding.passwordError.visibility = View.VISIBLE
        setEmailErrorState()
        setPasswordErrorState()
    }

    private fun onGoogleSignInSuccess() {
        val user = FirebaseAuth.getInstance().currentUser
        Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.etEmail.text.toString().trim()
            if (isValidEmail(email)) {
                setValidEmailState()
            } else {
                setEmailErrorState()
            }
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val password = binding.etPassword.text.toString().trim()
            if (isValidPassword(password)) {
                setValidPasswordState()
            } else {
                setPasswordErrorState()
            }
            updateButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun setValidEmailState() {
        binding.emailInputLayout.apply {
            setEndIconDrawable(validIcon)
            boxStrokeColor = ContextCompat.getColor(this@LoginActivity, R.color.green)
            setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@LoginActivity, R.color.green)))
        }
    }

    private fun setValidPasswordState() {
        binding.passwordInputLayout.apply {
            boxStrokeColor = ContextCompat.getColor(this@LoginActivity, R.color.green)
        }
        binding.passwordError.visibility = View.GONE
    }

    private fun updateButtonState() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val isEnabled = email.isNotEmpty() && password.isNotEmpty()
        binding.btnAksi.apply {
            alpha = if (isEnabled) 1.0f else 0.5f
            isClickable = isEnabled
            isFocusable = isEnabled
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
