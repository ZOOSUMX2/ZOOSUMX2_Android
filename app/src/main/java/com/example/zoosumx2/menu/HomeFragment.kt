package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zoosumx2.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        home_mission.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_main, MissionFragment())
            transaction.commit()
        }

        // 포인트 클릭
        linearlayout_mypoint_home.setOnClickListener {
            val intent = Intent(context, PointActivity::class.java)
            startActivity(intent)
        }
    }
}
