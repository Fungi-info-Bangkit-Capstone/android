package com.petershaan.fungiinfo_mobileapp.view.upgrade

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.petershaan.fungiinfo_mobileapp.R
import com.petershaan.fungiinfo_mobileapp.databinding.ActivityHomeBinding
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentHomeBinding
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentUpgradeBinding
import com.petershaan.fungiinfo_mobileapp.util.ImageViewModel


class UpgradeFragment : Fragment() {
    private var _binding: FragmentUpgradeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpgradeBinding.inflate(inflater, container, false)

        binding.btnUpgradePro.setOnClickListener {
            showComingSoonAlert()
        }

        return binding.root
    }

    private fun showComingSoonAlert() {
        AlertDialog.Builder(requireContext())
            .setTitle("Fungi Info Pro")
            .setMessage("Coming Soon")
            .setPositiveButton("OK", null)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}