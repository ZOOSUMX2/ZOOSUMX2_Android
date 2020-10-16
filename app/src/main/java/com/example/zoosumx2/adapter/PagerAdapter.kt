package com.example.zoosumx2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.zoosumx2.menu.HomeFragment
import com.example.zoosumx2.menu.MissionFragment

class PagerAdapter(fm: FragmentManager):FragmentPagerAdapter(fm) {

    val PAGE_MAX_CNT = 2
    override fun getCount(): Int {
        return PAGE_MAX_CNT
    }

    override fun getItem(position: Int): Fragment {
        val fragment = when(position){
            1->MissionFragment().newInstance()
            else->HomeFragment().newInstance()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position){
            1->"Mission"
            else->"Home"
        }
        return title
    }

}