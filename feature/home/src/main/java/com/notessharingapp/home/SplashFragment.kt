package com.notessharingapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.notessharingapp.data.LoginDataSource
import java.util.Timer
import kotlin.concurrent.schedule


class SplashFragment : Fragment() {

    val login = LoginDataSource()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val t = Timer()
        t.schedule(1000){
            requireActivity().runOnUiThread {

                if (login.isLoggedIn()){
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }else{
                    findNavController().navigate("login")
                }
            }
        }
    }

}