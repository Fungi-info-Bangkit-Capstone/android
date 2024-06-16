package com.petershaan.fungiinfo_mobileapp.view.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ArrayAdapter
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityAboutBinding
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityHomeBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener{
            finish()
        }

        val jamur = listOf<String>(
            "amanita",
            "boletus_edulis",
            "boletus_reticulatus",
            "flammulina_velutipes",
            "hericium_coralloides",
            "lactarius_deliciosus",
            "laetiporus_sulphureus",
            "phellinus_igniarius",
            "pleurotus_ostreatus",
            "suillus")
        val jenisJamur = StringBuilder()
        jamur.forEachIndexed { index, ciri ->
            jenisJamur.append("\u2022 $ciri\n")
        }
        binding.isiJudul4.text = jenisJamur.toString().trim()


        binding.peter.setOnClickListener {
            val profilPath = "https://www.linkedin.com/in/petershaan/"
            val installPackageName = "com.linkedin.android"
            openApp(profilPath, installPackageName)
        }
        binding.nandito.setOnClickListener {
            val profilPath = "https://www.linkedin.com/in/nanditosamosir/"
            val installPackageName = "com.linkedin.android"
            openApp(profilPath, installPackageName)
        }
        binding.arjuna.setOnClickListener {
            val profilPath = "https://www.linkedin.com/in/ariajuna-yodyatara-458b391a4/"
            val installPackageName = "com.linkedin.android"
            openApp(profilPath, installPackageName)
        }
        binding.gladwin.setOnClickListener {
            val profilPath = "https://www.linkedin.com/in/gladwin-riovaldy-99b839298/"
            val installPackageName = "com.linkedin.android"
            openApp(profilPath, installPackageName)
        }
        binding.ocha.setOnClickListener {
            val profilPath = "https://www.linkedin.com/in/anne-rossa-limbong-79b97a269/"
            val installPackageName = "com.linkedin.android"
            openApp(profilPath, installPackageName)
        }
    }

    private fun openApp(profilePath: String, installPackageName: String) {
        val uri = Uri.parse(profilePath)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        if (isAppInstalled(installPackageName)) {
            intent.setPackage(installPackageName)
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    private fun isAppInstalled(packageName: String): Boolean {
        val packageManager = packageManager
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }


}