package com.notessharingapp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.notessharingapp.login.databinding.FragmentCreateAccountBinding
import com.notessharingapp.login.viewmodel.CreateAccountViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateAccountFragment : Fragment() {

    private lateinit var binding: FragmentCreateAccountBinding
    private val viewModel by viewModels<CreateAccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateAccountBinding.inflate(inflater,container,false)
        binding.executePendingBindings()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.btnCreateAccount.setOnClickListener {
            viewModel.createAccount()
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner){
            println("LoginSucceess -----===>? $it")
            it?.let {success ->
               if (success){
                   findNavController().navigate("home")
                   findNavController().clearBackStack("home")
               }
            }
        }
    }


}