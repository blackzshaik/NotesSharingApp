package com.notessharingapp.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import com.notessharingapp.home.adapter.HomePagerAdapter
import com.notessharingapp.home.adapter.NotesAdapter
import com.notessharingapp.home.databinding.FragmentHomeBinding
import com.notessharingapp.home.viewmodel.HomeViewModel
import com.notessharingapp.model.Notes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    val viewModel by viewModels<HomeViewModel>()

    val menuHost by lazy {
        requireActivity() as MenuHost
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(viewModel.isLoggedIn.not()){
            findNavController().navigate("login")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewPager.adapter = HomePagerAdapter(this)
        binding.viewPager.registerOnPageChangeCallback(pageChangeCallback)

        binding.btnMe.setOnClickListener{
            binding.viewPager.currentItem = 0
            applyStyle(it as MaterialButton, true)
            applyStyle(binding.btnWorld, false)
        }

        binding.btnWorld.setOnClickListener{
            binding.viewPager.currentItem = 1
            applyStyle(it as MaterialButton, true)
            applyStyle(binding.btnMe, false)
        }

        menuHost.addMenuProvider(optionsMenu)
    }

    private val pageChangeCallback = object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            if(position == 0){
                applyStyle(binding.btnMe,true)
                applyStyle(binding.btnWorld,false)
            }else if(position == 1){
                applyStyle(binding.btnMe,false)
                applyStyle(binding.btnWorld,true)
            }
        }
    }

    fun applyStyle(materialButton:MaterialButton,selected:Boolean){
        materialButton.apply {
        if (selected) {

                setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.notessharingapp.ui.R.color.secondary
                    )
                )
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.notessharingapp.ui.R.color.primary
                    )
                )

            }else{
            setBackgroundColor(Color.parseColor("#222222"))
            setTextColor(ContextCompat.getColor(requireContext(), com.notessharingapp.ui.R.color.secondary))
            }
        }
    }

    private val optionsMenu = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

            menuInflater.inflate(com.notessharingapp.ui.R.menu.home_menu,menu)

        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when(menuItem.itemId){

                com.notessharingapp.ui.R.id.profile ->{
                    findNavController().navigate("profile")
                }
            }
            return true
        }

    }

    override fun onDestroyView() {
        menuHost.removeMenuProvider(optionsMenu)
        binding.viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        super.onDestroyView()

    }


}

