package com.petershaan.fungiinfo_mobileapp.view.analyze

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityAnalyzeBinding
import com.petershaan.fungiinfo_mobileapp.util.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class AnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnalyzeBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private val viewModel: AnalyzeViewModel by viewModels<AnalyzeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var result: ClassificationResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        if (intent.hasExtra(EXTRA_IMAGE_URI)) {
            val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
            imageUri?.let {
                binding.resultImage.setImageURI(it)
            }
            setupImageClassifier(imageUri)
            imageClassifierHelper.classifyStaticImage(imageUri)

            binding.btnAksi.setOnClickListener {
                result?.let { viewModel.insert(it) }
                finish()
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
                    viewModel.delete(result!!)
                    finish()
                }
            }
            binding.btnAksi.text = getString(R.string.delete)
        }
    }

    private fun setupImageClassifier(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(this@AnalyzeActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { classifications ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                                println(classifications)
                                classifications[0].categories.maxByOrNull { it.score }!!.let {
                                    updateView(imageUri, it.label, it.score)
                                    result = ClassificationResult(
                                        timestamp = System.currentTimeMillis(),
                                        imageUri = imageUri.toString(),
                                        label = it.label,
                                        score = it.score
                                    )
                                }
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )
    }

    private fun updateView(imageUri: Uri, label: String, score: Float) {
        binding.resultImage.setImageURI(imageUri)
        binding.resultText.text =
            buildString {
                append(NumberFormat.getPercentInstance().format(score))
                append(" $label")
            }
    }


    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}