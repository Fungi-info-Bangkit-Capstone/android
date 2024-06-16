package com.petershaan.fungiinfo_mobileapp.view.analyze

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.data.daftarJamur
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityAnalyzeBinding
import com.petershaan.fungiinfo_mobileapp.helper.ImageClassifierHelper
import com.petershaan.fungiinfo_mobileapp.util.ViewModelFactory


class AnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyzeBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private val viewModel: AnalyzeViewModel by viewModels<AnalyzeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var result: ClassificationResult? = null
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)


        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        userId = currentUser?.uid ?: "defaultUserId"

        if (intent.hasExtra(EXTRA_IMAGE_URI)) {
            val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
            imageUri?.let {
                binding.resultImage.setImageURI(it)
            }
            showLoading(true)
            setupImageClassifier(imageUri)
            imageClassifierHelper.classifyStaticImage(imageUri)

            binding.btnAksi.setOnClickListener {
                showLoading(true)
                Handler(Looper.getMainLooper()).postDelayed({
                    result?.let {
                        viewModel.insert(it)
                        showLoading(false)
                        finish()
                    }
                }, 200)
            }
        } else if (intent.hasExtra(EXTRA_RESULT)) {
            result = if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableExtra(EXTRA_RESULT, ClassificationResult::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_RESULT)
            }

            result?.let {
                updateView(Uri.parse(it.imageUri), it.label, it.score)
                binding.btnAksi.setOnClickListener {
                    showLoading(true)
                    viewModel.delete(result!!)
                    showLoading(false)
                    finish()
                }
            }
            binding.btnAksi.text = getString(R.string.hapus)
        }
    }

    private fun setupImageClassifier(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        showLoading(false)
                        Toast.makeText(this@AnalyzeActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Pair<String?, Float>>?) {
                    runOnUiThread {
                        showLoading(false)
                        results?.let { labeledResults ->
                            if (labeledResults.isNotEmpty()) {
                                // Assume the highest score corresponds to the classification result
                                val (label, maxScore) = labeledResults.maxByOrNull { it.second } ?: ("Unknown" to 0f)
                                updateView(imageUri, label ?: "unkown", maxScore)
                                result = ClassificationResult(
                                    timestamp = System.currentTimeMillis(),
                                    imageUri = imageUri.toString(),
                                    label = label ?: "unknown",
                                    score = maxScore,
                                    userId = userId,
                                )
                            } else {
                                binding.jenisJamur.text = ""
                            }
                        }
                    }
                }
            }
        )
    }


    private fun updateView(imageUri: Uri, label: String, score: Float) {
        binding.resultImage.setImageURI(imageUri)
        binding.akurasiPersen.progress = (score * 100).toInt()

        val progressAnimator = ObjectAnimator.ofInt(binding.akurasiPersen, "progress", 0, (score * 100).toInt())
        progressAnimator.duration = 1500
        progressAnimator.start()

        val currentScore = binding.persen.text.toString().removeSuffix("%").toIntOrNull() ?: 0
        val newScore = (score * 100).toInt()
        val textAnimator = ValueAnimator.ofInt(currentScore, newScore)
        textAnimator.duration = 2000
        textAnimator.addUpdateListener { animation ->
            binding.persen.text = getString(R.string.persen, animation.animatedValue as Int)
        }
        textAnimator.start()

        val detectedJamur = daftarJamur.find { it.label == label }
        detectedJamur?.let { jamur ->
            binding.jenisJamur.text = jamur.nama
            binding.isiDeskripsi.text = jamur.deskripsi

            val ciriCiriBuilder = StringBuilder()
            jamur.ciri_ciri.forEachIndexed { index, ciri ->
                ciriCiriBuilder.append("\u2022 $ciri\n\n")
            }
            binding.isiCiriCiri.text = ciriCiriBuilder.toString().trim()

            if (jamur.label == "amanita" || jamur.label == "phellinus_igniarius" || jamur.label == "suillus") {
                binding.mengapaHarusDihindariLabel.visibility = View.VISIBLE
                binding.manfaatBagiKesehatanLabel.visibility = View.GONE
            } else {
                binding.mengapaHarusDihindariLabel.visibility = View.GONE
                binding.manfaatBagiKesehatanLabel.visibility = View.VISIBLE
            }

            when (jamur.manfaat_bahaya) {
                is String -> {
                    binding.mengapaDihindari.text = jamur.manfaat_bahaya as String
                }
                is List<*> -> {
                    val manfaatBuilder = StringBuilder()
                    (jamur.manfaat_bahaya as List<String>).forEachIndexed { index, manfaat ->
                        manfaatBuilder.append("${index + 1}. $manfaat\n\n")
                    }
                    binding.mengapaDihindari.text = manfaatBuilder.toString().trim()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}
