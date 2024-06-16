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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentHomeBinding
import com.petershaan.fungiinfo_mobileapp.util.ImageViewModel
import com.petershaan.fungiinfo_mobileapp.view.analyze.AnalyzeActivity
import com.petershaan.fungiinfo_mobileapp.view.analyze.CameraActivity
import com.petershaan.fungiinfo_mobileapp.view.analyze.CameraActivity.Companion.CAMERAX_RESULT
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ImageViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    private val requestGalleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            Log.d("HomeFragment", "requestGalleryPermissionLauncher: $isGranted")
            if (isGranted) {
                startGallery()
            } else {
                Toast.makeText(requireContext(), "Gallery permission denied", Toast.LENGTH_LONG).show()
            }
        }

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

        viewModel.currentImageUri?.let { uri ->
            binding.previewImageView.setImageURI(uri)
        }

        updateButtonStatus()

        binding.galeri.setOnClickListener {
            startGallery()
        }

        binding.camera.setOnClickListener {
            startCameraX()
        }

        binding.analyze.setOnClickListener {
            analyzeImage()
        }

    }

    private fun startCameraX() {
        val intent = Intent(context, CameraActivity::class.java)
        startActivityForResult(intent, CAMERAX_RESULT)
    }

    private fun startGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            launcherGallery.launch("image/*")
        } else {
            requestGalleryPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
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
        Log.d(TAG, "onActivityResult: requestCode: $requestCode, resultCode: $resultCode")
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageUriString = data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)
                    if (imageUriString != null) {
                        val imageUri = imageUriString.toUri()
                        moveToCrop(imageUri)
                    } else {
                        showToast(getString(R.string.gagal_ambil_foto))
                    }
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
            Log.d(TAG, "onActivityResult: $errorMessage")
        }
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
        private const val REQUEST_IMAGE_CAPTURE = 200
        private const val REQUEST_IMAGE_GALLERY = 201
    }
}
