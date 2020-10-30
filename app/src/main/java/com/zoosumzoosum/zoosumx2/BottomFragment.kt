package com.zoosumzoosum.zoosumx2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoosumzoosum.zoosumx2.adapter.RegionAdapter
import com.zoosumzoosum.zoosumx2.model.RegionData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragment(): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = inflater.inflate(R.layout.region_bottom_sheet_dialog, container, false)

        val data: MutableList<RegionData> = setData()
        val adapter = RegionAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.region_item_grid)
        adapter.regionData = data
        recyclerView.adapter = adapter

        val gridLayoutManager = GridLayoutManager(activity!!.applicationContext, 3)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.setHasFixedSize(true)

        return view
    }

    private fun setData(): MutableList<RegionData> {
        val data: MutableList<RegionData> = mutableListOf()
        /*for(num in 1..24){
            var regiondata = RegionData(num.toString())
            data.add(regiondata)
        }*/
        data.add(RegionData("용산구"))
        data.add(RegionData("종로구"))
        data.add(RegionData("중구"))
        data.add(RegionData("성동구"))
        data.add(RegionData("광진구"))
        data.add(RegionData("동대문구"))
        data.add(RegionData("중랑구"))
        data.add(RegionData("성북구"))
        data.add(RegionData("강북구"))
        data.add(RegionData("마포구"))
        data.add(RegionData("은평구"))
        data.add(RegionData("양천구"))
        data.add(RegionData("구로구"))
        data.add(RegionData("영등포구"))
        data.add(RegionData("동작구"))
        data.add(RegionData("관악구"))
        data.add(RegionData("서초구"))
        data.add(RegionData("금천구"))
        data.add(RegionData("강남구"))
        data.add(RegionData("송파구"))
        data.add(RegionData("강동구"))
        data.add(RegionData("노원구"))
        data.add(RegionData("도봉구"))
        data.add(RegionData("강서구"))

        return data
    }
}
