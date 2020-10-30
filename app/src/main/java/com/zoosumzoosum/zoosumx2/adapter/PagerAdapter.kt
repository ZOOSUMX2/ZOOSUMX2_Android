package com.zoosumzoosum.zoosumx2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zoosumzoosum.zoosumx2.menu.HomeFragment
import com.zoosumzoosum.zoosumx2.menu.MissionFragment

class PagerAdapter(fm: FragmentManager):FragmentPagerAdapter(fm) {

    private val pageMaxCnt = 2
    override fun getCount(): Int {
        return pageMaxCnt
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> MissionFragment().newInstance()
            else -> HomeFragment().newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "Mission"
            else -> "Home"
        }
    }

}