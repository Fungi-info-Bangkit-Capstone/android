package com.petershaan.fungiinfo_mobileapp.view.loginRegister

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityLupaPasswordBinding

class LupaPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLupaPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener{
            val email = binding.etEmail.text.toString().trim()
            if(email.isEmpty()){
                binding.emailError.text = "Email harus diisi"
                binding.emailError.visibility = View.VISIBLE
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)
                binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)))
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.emailError.text = "Email tidak valid"
                binding.emailError.visibility = View.VISIBLE
                binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)
                binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)))
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Email reset password telah dikirim", Toast.LENGTH_SHORT).show()
                        Intent(this@LupaPasswordActivity, LoginActivity::class.java).also {intent ->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        binding.emailError.text = "${it.exception?.message}"
                        binding.emailError.visibility = View.VISIBLE
                        binding.emailInputLayout.boxStrokeColor = ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)
                        binding.emailInputLayout.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this@LupaPasswordActivity, R.color.red)))
                        binding.etEmail.requestFocus()
                    }
                }

        }

    }
}