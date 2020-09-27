package com.example.zoosumx2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.region_bottom_sheet_dialog.*


class BottomFragment(): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.region_bottom_sheet_dialog,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //초기 상태
        jong_ro.isSelected=false
        joong_gu.isSelected=false
        yong_san.isSelected=false

        jong_ro.setOnClickListener {
            if(jong_ro.isSelected){
                jong_ro.isSelected=false
                jong_ro.setTextColor(Color.parseColor("#555555"))
            }
            else{
                jong_ro.isSelected=true
                jong_ro.setTextColor(Color.parseColor("#ffffff"))

                joong_gu.isSelected=false
                joong_gu.setTextColor(Color.parseColor("#555555"))

                yong_san.isSelected=false
                yong_san.setTextColor(Color.parseColor("#555555"))

                val intent = Intent(activity, GetRegionActivity::class.java)
                intent.putExtra("user_region",jong_ro.text.toString())
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent)
            }
        }

        joong_gu.setOnClickListener {
            if(joong_gu.isSelected){
                joong_gu.isSelected=false
                joong_gu.setTextColor(Color.parseColor("#555555"))
            }
            else{
                jong_ro.isSelected=false
                jong_ro.setTextColor(Color.parseColor("#555555"))

                joong_gu.isSelected=true
                joong_gu.setTextColor(Color.parseColor("#ffffff"))

                yong_san.isSelected=false
                yong_san.setTextColor(Color.parseColor("#555555"))

                val intent = Intent(activity, GetRegionActivity::class.java)
                intent.putExtra("user_region",joong_gu.text.toString())
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent)
            }
        }

        yong_san.setOnClickListener {
            if(yong_san.isSelected){
                yong_san.isSelected=false
                yong_san.setTextColor(Color.parseColor("#555555"))
            }
            else{
                jong_ro.isSelected=false
                jong_ro.setTextColor(Color.parseColor("#555555"))

                joong_gu.isSelected=false
                joong_gu.setTextColor(Color.parseColor("#555555"))

                yong_san.isSelected=true
                yong_san.setTextColor(Color.parseColor("#ffffff"))

                val intent = Intent(activity, GetRegionActivity::class.java)
                intent.putExtra("user_region",yong_san.text.toString())
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent)
            }
        }
    }
}