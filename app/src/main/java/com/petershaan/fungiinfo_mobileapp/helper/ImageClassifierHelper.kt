package com.petershaan.fungiinfo_mobileapp.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.ml.Model3
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier


class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "model3.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?,
) {

    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

//        try {
//            imageClassifier = ImageClassifier.createFromFileAndOptions(
//                context,
//                modelName,
//                optionsBuilder.build()
//            )
//        } catch (e: IllegalStateException) {
//            classifierListener?.onError(context.getString(R.string.gagal))
//            Log.e(TAG, e.message.toString())
//        }
    }


    fun classifyStaticImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(255, 255, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.FLOAT32))
            .build()

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }.copy(Bitmap.Config.ARGB_8888, true)

        bitmap?.let {
            val tensorImage = imageProcessor.process(TensorImage.fromBitmap(it))

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 255, 255, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            val model = Model3.newInstance(context)

            try {
                // Run model inference and get result.
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                // Extract results and map to labels
                val labels = arrayOf(
                    "amanita",
                    "boletus_edulis",
                    "boletus_reticulatus",
                    "flammulina_velutipes",
                    "hericium_coralloides",
                    "lactarius_deliciosus",
                    "laetiporus_sulphureus",
                    "phellinus_igniarius",
                    "pleurotus_ostreatus",
                    "suillus"
                )

                val probabilities = outputFeature0.floatArray
                val results = probabilities?.mapIndexed { index, probability ->
                    labels.getOrNull(index) to probability
                }?.sortedByDescending { it.second }

                classifierListener?.onResults(results)
            } catch (e: Exception) {
                Log.e(TAG, "Error running inference: ${e.message}")
                classifierListener?.onError(context.getString(R.string.gagal))
            } finally {
                // Release model resources if no longer used.
                model.close()
                imageClassifier?.close()
            }
        }
    }



    interface ClassifierListener {
        fun onResults(results: List<Pair<String?, Float>>?)
        fun onError(error: String)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

}
