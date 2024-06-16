package com.petershaan.fungiinfo_mobileapp.view.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityMainBinding
import com.petershaan.fungiinfo_mobileapp.view.about.AboutActivity
import com.petershaan.fungiinfo_mobileapp.view.home.HomeActivity
import com.petershaan.fungiinfo_mobileapp.view.login.LoginActivity
import com.petershaan.fungiinfo_mobileapp.view.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupVideoBackground()
        setupClickListeners()

        viewModel.auth = FirebaseAuth.getInstance()

        binding.logo.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupVideoBackground() {
        val uri = Uri.parse("android.resource://$packageName/${R.raw.bg_jamur}")
        binding.bgVideo.setVideoURI(uri)
        binding.bgVideo.start()

        binding.bgVideo.setOnPreparedListener { mp ->
            mp.isLooping = true
        }
    }

    private fun setupClickListeners() {
        binding.button1.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.currentPosition = binding.bgVideo.currentPosition
        binding.bgVideo.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.bgVideo.seekTo(viewModel.currentPosition)
        binding.bgVideo.start()
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isUserLoggedIn()) {
            Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        }
    }
}
