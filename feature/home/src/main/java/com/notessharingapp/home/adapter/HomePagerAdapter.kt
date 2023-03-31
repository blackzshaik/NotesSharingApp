package com.notessharingapp.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.notessharingapp.home.MeFragment
import com.notessharingapp.home.WorldFragment

class HomePagerAdapter (fragment:Fragment): FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if(position == 0) {
             MeFragment()
        }else {
             WorldFragment()
        }
    }
}