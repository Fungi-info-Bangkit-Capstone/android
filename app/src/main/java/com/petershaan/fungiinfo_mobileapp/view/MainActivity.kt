package com.petershaan.fungiinfo_mobileapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityMainBinding
import com.petershaan.fungiinfo_mobileapp.view.loginRegister.LoginActivity
import com.petershaan.fungiinfo_mobileapp.view.loginRegister.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentPosition: Int = 0
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val uri = Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.bg_jamur);
        binding.bgVideo.setVideoURI(uri);
        binding.bgVideo.start();

        binding.bgVideo.setOnPreparedListener { mp ->
            mp.isLooping = true
        }

        binding.Button1.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }

        binding.Button2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }

    override fun onPause() {
        super.onPause()
        currentPosition = binding.bgVideo.currentPosition
        binding.bgVideo.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.bgVideo.seekTo(currentPosition)
        binding.bgVideo.start()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@MainActivity, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}