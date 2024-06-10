package com.petershaan.fungiinfo_mobileapp.view.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentHomeBinding
import com.petershaan.fungiinfo_mobileapp.util.ImageViewModel
import com.petershaan.fungiinfo_mobileapp.view.analyze.AnalyzeActivity
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImageViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                moveToCrop(uri)
                viewModel.currentImageUri = uri
                showImage()
            } else {
                showToast(getString(R.string.gagal_ambil_foto))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            binding.judul.text = user.displayName ?: "Kamu"
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }


        viewModel.currentImageUri?.let { uri ->
            binding.previewImageView.setImageURI(uri)
        }

        updateButtonStatus()

        binding.galeri.setOnClickListener {
            startGallery()
        }

        binding.camera.setOnClickListener {
            checkCameraPermissionAndStart()
        }

        binding.analyze.setOnClickListener {
            analyzeImage()
        }

    }



    private fun checkCameraPermissionAndStart() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun analyzeImage() {
        val context = requireContext()
        val intent = Intent(context, AnalyzeActivity::class.java)
        intent.putExtra(AnalyzeActivity.EXTRA_IMAGE_URI, viewModel.currentImageUri.toString())
        startActivity(intent)
    }

    private fun showImage() {
        viewModel.currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
        updateButtonStatus()
    }

    private fun moveToCrop(uri: Uri) {
        val context = requireContext()
        val cacheDir = context.cacheDir
        val destinationUri = Uri.fromFile(File(cacheDir, "${System.currentTimeMillis()}.jpg"))
        UCrop.of(uri, destinationUri)
            .start(context, this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val imageUri = getImageUri(requireContext(), imageBitmap)
                    moveToCrop(imageUri)
                }
                UCrop.REQUEST_CROP -> {
                    val resultUri = UCrop.getOutput(data!!)
                    viewModel.currentImageUri = resultUri
                    showImage()
                }
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        moveToCrop(selectedImageUri)
                    }
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val errorMessage = UCrop.getError(data!!)?.message.toString()
            showToast(errorMessage)
            Log.e(TAG, errorMessage)
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    private fun updateButtonStatus() {
        val isImagePresent = viewModel.currentImageUri != null
        binding.analyze.isEnabled = isImagePresent
        binding.analyze.alpha = if (isImagePresent) 1.0f else 0.3f
    }

    private fun showToast(message: String) {
        val context = requireContext()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_IMAGE_GALLERY = 200
    }
}
