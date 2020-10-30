package com.zoosumzoosum.zoosumx2.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.zoosumzoosum.zoosumx2.R
import com.zoosumzoosum.zoosumx2.adapter.PagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class PagerHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_pager_home, container, false)

        val pagerAdapter = PagerAdapter(childFragmentManager)
        val pager: ViewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val dotsIndicator = view.findViewById<DotsIndicator>(R.id.indicator)
        pager.adapter = pagerAdapter
        dotsIndicator.setViewPager(pager)

        return view
    }

}