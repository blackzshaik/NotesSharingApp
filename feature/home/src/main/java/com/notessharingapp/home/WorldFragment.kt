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
import com.notessharingapp.home.databinding.FragmentWorldBinding
import com.notessharingapp.home.viewmodel.WorldViewModel
import com.notessharingapp.model.Notes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class WorldFragment : Fragment() {


    private val viewModel: WorldViewModel by viewModels()
    private lateinit var binding: FragmentWorldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorldBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadNotes()

    }

    private  fun loadNotes() {

        viewModel.notesFromWorld.observe(viewLifecycleOwner){
            Log.d("MeFragment","list collected $it")
            binding.rvNotes.adapter = NotesAdapter(it,onClickNote)
        }

    }

    private val onClickNote = fun (note: Notes){
        findNavController().navigate("notes/add?notes=${note.notesId}")
    }

}