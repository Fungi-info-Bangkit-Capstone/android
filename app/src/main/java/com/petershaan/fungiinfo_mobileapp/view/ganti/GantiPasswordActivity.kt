package com.petershaan.fungiinfo_mobileapp.view.ganti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityGantiPasswordBinding
import com.petershaan.fungiinfo_mobileapp.view.main.MainActivity

class GantiPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGantiPasswordBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGantiPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        binding.pertama.visibility = View.VISIBLE
        binding.kedua.visibility = View.GONE
        binding.btnAksi.setOnClickListener{
            val password = binding.etPassword.text.toString().trim()
            if(password.isEmpty()){
                binding.etPassword.error = "Masukkan Password Kamu"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            user.let {
               val userCredential = EmailAuthProvider.getCredential(it?.email!!, password)
               it.reauthenticate(userCredential).addOnCompleteListener { Task ->
                   when {
                       Task.isSuccessful -> {
                           binding.pertama.visibility = View.GONE
                           binding.kedua.visibility = View.VISIBLE
                       }

                       Task.exception is FirebaseAuthInvalidCredentialsException -> {
                           binding.passwordError.text = "Password Salah"
                           binding.passwordError.visibility = View.VISIBLE
                           binding.passwordInputLayout.boxStrokeColor = ContextCompat.getColor(this@GantiPasswordActivity, R.color.red)
                           binding.etPassword.requestFocus()
                       }

                       else -> {
                           Toast.makeText(this, "${Task.exception?.message}", Toast.LENGTH_SHORT)
                               .show()
                       }
                   }
               }
           }
            binding.btnSave.setOnClickListener updatePassword@{
                    val newPassword = binding.etNewPassword.text.toString().trim()
                    if (newPassword.isEmpty()) {
                        binding.passwordError2.text = "Masukkan Password Baru"
                        binding.passwordError2.visibility = View.VISIBLE
                        binding.passwordNewInputLayout.boxStrokeColor = ContextCompat.getColor(this@GantiPasswordActivity, R.color.red)
                        binding.etNewPassword.requestFocus()
                        return@updatePassword
                    }
                    if (newPassword.length < 6) {
                        binding.passwordError2.text = "Password harus lebih dari 6 karakter"
                        binding.passwordError2.visibility = View.VISIBLE
                        binding.passwordNewInputLayout.boxStrokeColor = ContextCompat.getColor(this@GantiPasswordActivity, R.color.red)
                        binding.etNewPassword.requestFocus()
                        return@updatePassword
                    }
                    user?.let {
                        user.updatePassword(newPassword).addOnCompleteListener { Task ->
                            if (Task.isSuccessful) {
                                auth.signOut()
                                Intent(this, MainActivity::class.java).also {
                                    Toast.makeText(this, "Password berhasil diubah Silahkan Login", Toast.LENGTH_SHORT)
                                        .show()
                                    it.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(it)
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "${Task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
