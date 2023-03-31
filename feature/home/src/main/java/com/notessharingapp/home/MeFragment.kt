package com.notessharingapp.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.notessharingapp.home.adapter.NotesAdapter
import com.notessharingapp.home.databinding.FragmentMeBinding
import com.notessharingapp.home.viewmodel.MeViewModel
import com.notessharingapp.model.Notes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeFragment : Fragment() {


    private val viewModel: MeViewModel by viewModels()
    private lateinit var binding: FragmentMeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createNote.setOnClickListener {
            findNavController().navigate("notes/add?notes=")
        }
        loadNotes()


    }
    private  fun loadNotes() {

        viewModel.getMyNotes().observe(viewLifecycleOwner){
            Log.d("MeFragment","list collected")
                binding.rvNotes.adapter = NotesAdapter(it.sortedByDescending { it.createdAt },onClickNote)
        }

    }

    private val onClickNote = fun (note: Notes){
        findNavController().navigate("notes/add?notes=${note.notesId}")
    }
}

