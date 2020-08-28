package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zoosumx2.R
import com.example.zoosumx2.SettingActivity
import com.example.zoosumx2.StoreActivity
import com.example.zoosumx2.TownActivity
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 주섬주섬 상점
        linearlayout_store_mypage.setOnClickListener {
            val intent = Intent(context, StoreActivity::class.java)
            startActivity(intent)
        }

        // 우리동네 현황
        linearlayout_town_mypage.setOnClickListener {
            val intent = Intent(context, TownActivity::class.java)
            startActivity(intent)
        }

        // 환경설정
        linearlayout_setting_mypage.setOnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

    }
}