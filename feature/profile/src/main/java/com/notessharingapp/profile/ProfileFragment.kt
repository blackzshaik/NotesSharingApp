package com.notessharingapp.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.notessharingapp.profile.databinding.FragmentProfileBinding
import com.notessharingapp.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    val viewModel by  viewModels<ProfileViewModel>()

    val menuHost by lazy {
        requireActivity() as MenuHost
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        menuHost.addMenuProvider(optionsMenu)
    }

    private val optionsMenu = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

//            menuInflater.inflate(com.notessharingapp.ui.R.menu.home_menu,menu)

        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when(menuItem.itemId){

                android.R.id.home ->{
                    findNavController().navigateUp()
                }
            }
            return true
        }

    }

    override fun onDestroyView() {
        menuHost.removeMenuProvider(optionsMenu)
        super.onDestroyView()
    }

}