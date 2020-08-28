package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zoosumx2.MakequizActivity
import com.example.zoosumx2.R
import com.example.zoosumx2.ResidentQuizActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    //Todo: 전역변수 정의
    //Fixme:

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = imagebutton_general_quiz_home
        button.setOnClickListener {
            val intent = Intent(context, MakequizActivity::class.java)
            startActivity(intent)
        }

        val button2 = imagebutton_resident_quiz_home
        button2.setOnClickListener {
            val intent = Intent(context, ResidentQuizActivity::class.java)
            startActivity(intent)
        }
    }
}