package com.petershaan.fungiinfo_mobileapp.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.petershaan.fungiinfo_mobileapp.data.local.ClassificationResult
import com.petershaan.fungiinfo_mobileapp.databinding.FragmentHistoryBinding
import com.petershaan.fungiinfo_mobileapp.util.ViewModelFactory
import com.petershaan.fungiinfo_mobileapp.view.analyze.AnalyzeAdapter


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setupRecyclerView()
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData() {
        auth.currentUser?.let { user ->
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
            viewModel.results.observe(viewLifecycleOwner) { results ->
                setResults(results)
                toggleEmptyView(results)
            }
            viewModel.loadResultsForUser(user.uid, viewLifecycleOwner)
        }
    }

    private fun setResults(results: List<ClassificationResult>) {
        val adapter = AnalyzeAdapter()
        adapter.submitList(results)
        binding.simpanJamur.adapter = adapter
    }

    private fun toggleEmptyView(results: List<ClassificationResult>) {
        if (results.isEmpty()) {
            binding.simpanJamur.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.simpanJamur.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.simpanJamur.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.simpanJamur.addItemDecoration(itemDecoration)
    }
}