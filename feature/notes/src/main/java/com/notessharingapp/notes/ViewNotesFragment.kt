package com.notessharingapp.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.notessharingapp.notes.databinding.FragmentViewNotesBinding
import com.notessharingapp.notes.viewmodel.ViewNotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewNotesFragment : Fragment() {

    private lateinit var binding : FragmentViewNotesBinding
    val viewModel by viewModels<ViewNotesViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewNotesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
    }

}