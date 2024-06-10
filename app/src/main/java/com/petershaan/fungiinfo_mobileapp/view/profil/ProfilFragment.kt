package com.petershaan.fungiinfo_mobileapp.view.profil

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentProfilBinding
import com.petershaan.fungiinfo_mobileapp.view.MainActivity
import com.petershaan.fungiinfo_mobileapp.view.home.HomeFragment
import com.petershaan.fungiinfo_mobileapp.view.loginRegister.GantiPasswordActivity
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfilFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri: Uri

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.ivProfile)
            } else {
                Picasso.get().load(R.drawable.image_preview).into(binding.ivProfile)
            }
            binding.etName.setText(user.displayName)
            binding.etEmail.setText(user.email)
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.etName.addTextChangedListener(textWatcher)
        binding.etEmail.addTextChangedListener(textWatcher)

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val context = requireContext()
            Intent(context, MainActivity::class.java).also {
                Toast.makeText(context, "Kamu Berhasil Logout", Toast.LENGTH_SHORT).show()
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        binding.btnReset.setOnClickListener {
            val context = requireContext()
            Intent(context, GantiPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.ivProfile.setOnClickListener {
            showImagePickerOptions()
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


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("ProfilFragment", "Halo aku berjalan")
            updateButtonState()
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private fun updateButtonState() {
        val name = binding.etName.text.toString().trim()
        val isNameChanged = name != auth.currentUser?.displayName
        val isEnabled = isNameChanged

        binding.btnSave.alpha = if (isEnabled) 1.0f else 0.5f
        binding.btnSave.isClickable = isEnabled
        if (isEnabled) {
            binding.btnSave.setOnClickListener {
                updateProfile(name)
            }
        } else {
            binding.btnSave.setOnClickListener(null)
        }
    }

    private fun updateProfile(name: String) {
        val user = auth.currentUser
        binding.progressBar.visibility = View.VISIBLE
        val image = when {
            ::imageUri.isInitialized -> imageUri
            user?.photoUrl == null -> Uri.parse(R.drawable.image_preview.toString())
            else -> user.photoUrl
        }
        if (name.isEmpty()) {
            binding.etName.error = "Nama harus diisi"
            binding.etName.requestFocus()
            return
        }
        if (name.length > 13) {
            binding.etName.error = "Nama tidak boleh lebih dari 13 huruf"
            binding.etName.requestFocus()
            return
        }

        UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(image)
            .build()
            .also {
                user?.updateProfile(it)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Profil berhasil diupdate", Toast.LENGTH_SHORT).show()
                        binding.btnSave.alpha = 0.5f
                        binding.etName.clearFocus()
                    } else {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


    private fun showImagePickerOptions() {
        val options = arrayOf("Ambil Foto", "Pilih dari Galeri")
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Pilih Gambar")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> checkCameraPermissionAndStart()
                    1 -> intentGallery()
                }
            }
            .show()
    }

    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
    }

    private fun intentGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { intent ->
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    uploadImgBitmap(imageBitmap)
                }
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImageUri = data?.data
                    selectedImageUri?.let {
                        uploadImgUri(it)
                    }
                }
            }
        }
    }

    private fun uploadImgBitmap(imageBitmap: Bitmap) {
        binding.progressBar.visibility = View.VISIBLE

        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img_profile/${FirebaseAuth.getInstance().currentUser?.uid}")
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        ref.putBytes(data).addOnCompleteListener {
            binding.progressBar.visibility = View.GONE

            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { task ->
                    task.result?.let { uri ->
                        imageUri = uri
                        binding.ivProfile.setImageBitmap(imageBitmap)
                    }
                }
                Toast.makeText(requireContext(), "Berhasil Upload Silahkan Save", Toast.LENGTH_SHORT).show()
                val name = binding.etName.text.toString().trim()
                binding.btnSave.alpha = 1.0f
                binding.btnSave.isClickable = true
                binding.btnSave.setOnClickListener {
                    updateProfile(name)
                }
            } else {
                Toast.makeText(requireContext(), "Gagal Upload", Toast.LENGTH_SHORT).show()
                Log.d("ProfilFragment", "uploadImgBitmap: ${it.exception?.message}")
            }
        }
    }

    private fun uploadImgUri(selectedImageUri: Uri) {
        binding.progressBar.visibility = View.VISIBLE

        val ref = FirebaseStorage.getInstance().reference.child("img_profile/${FirebaseAuth.getInstance().currentUser?.uid}")
        ref.putFile(selectedImageUri).addOnCompleteListener {
            binding.progressBar.visibility = View.GONE

            if (it.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { task ->
                    task.result?.let { uri ->
                        imageUri = uri
                        binding.ivProfile.setImageURI(selectedImageUri)
                    }
                }
                Toast.makeText(requireContext(), "Berhasil Upload", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Gagal Upload", Toast.LENGTH_SHORT).show()
                Log.d("ProfilFragment", "uploadImgUri: ${it.exception?.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 100
        private const val REQUEST_IMAGE_GALLERY = 200
    }
}
