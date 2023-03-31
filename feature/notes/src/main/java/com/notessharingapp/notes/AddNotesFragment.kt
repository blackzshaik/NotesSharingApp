package com.notessharingapp.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.notessharingapp.notes.databinding.FragmentAddNotesBinding
import com.notessharingapp.notes.viewmodel.AddNotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.notessharingapp.ui.hide
import com.notessharingapp.ui.show
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class AddNotesFragment : Fragment() {

    val viewModel by viewModels<AddNotesViewModel>()
    private lateinit var binding: FragmentAddNotesBinding
    val menuHost by lazy {
        requireActivity() as MenuHost
    }
    private lateinit var bsBehavior: BottomSheetBehavior<LinearLayoutCompat>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            println("onBackPressedDispacther ..... !!!!")
            saveAndGoBack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        arguments?.getString("notesId").let {
            println("Args NotesId : $it")
            if (it.isNullOrEmpty()) {
                println("IT is null or empty")
                menuHost.addMenuProvider(optionsMenu)
                binding.author.hide()
                binding.createdOn.hide()
            } else {
                println("Args NotesId : $it")
                showOldNote(it)

                binding.author.show()
                binding.createdOn.show()
            }
        }

        bsBehavior = BottomSheetBehavior.from(binding.shareBs.root as LinearLayoutCompat)


//        requireActivity().actionBar!!.title = "it.title"
    }

    private val optionsMenu = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            if (arguments?.getString("notesId").isNullOrEmpty().not()) {
                menuInflater.inflate(com.notessharingapp.ui.R.menu.my_notes_option_menu, menu)
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                android.R.id.home -> {
                    saveAndGoBack()
                    return true
                }

                com.notessharingapp.ui.R.id.delete -> {
                    showDeleteDialog()
                }

                com.notessharingapp.ui.R.id.share -> {

                    bsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            return false
        }

    }

    fun saveAndGoBack() {
        viewModel.saveNote(requireContext())
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        menuHost.removeMenuProvider(optionsMenu)
        super.onDestroyView()
    }


    private fun showOldNote(it: String) {

        binding.title.clearFocus()
        lifecycleScope.launch {
            viewModel.getNoteById(it).catch {
                println("SOme error occcured $it")
            }.collect {
                viewModel.oldNote = it
                viewModel.notesId = it.notesId
                viewModel.note.value = it.note
                viewModel.title.value = it.title
                viewModel.author.value = "by @" + it.authorNickName
                val sdf = SimpleDateFormat("MMM-dd-yyyy HH:mm")
                viewModel.createdOn.value = "Created on : ${sdf.format(it.createdAt)}"
                binding.shareBs.switchPublic.isChecked = it.public
                println("isPublic ===> ${it.public}")
                binding.shareBs.switchPublic.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked != it.public) {
                        println("is checked new state $isChecked")
                        viewModel.changeShareOption(isChecked)
                    }
                }
                if (viewModel.isMyNote()) {
                    menuHost.addMenuProvider(optionsMenu)
                } else {
                    if(it.title.isEmpty()){
                        binding.title.hide()
                    }
                    binding.imgSave.hide()
                    binding.title.isEnabled = false
                    binding.title.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            com.notessharingapp.ui.R.color.secondary
                        )
                    )
                    binding.notes.isEnabled = false
                    binding.notes.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            com.notessharingapp.ui.R.color.secondary
                        )
                    )
                }
            }
        }
    }

    fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you sure?")
            .setMessage("You want to delete this note")
            .setIcon(com.notessharingapp.ui.R.drawable.baseline_delete_forever_24)
            .setNegativeButton("No", {dialog , which -> })
            .setPositiveButton("Yes") { dialog, which ->
                lifecycleScope.launch {
                    viewModel.deleteThisNote().collect{
                        if (it){
                            findNavController().navigateUp()
                        }else{
                            println("Some error occurede when deleteing")
                        }

                    }
                }
            }
            .show()
    }

}