package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.zoosumx2.MailboxFirstActivity
import com.example.zoosumx2.MakequizActivity
import com.example.zoosumx2.R
import com.example.zoosumx2.ResidentQuizActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_mailbox.*

class HomeFragment : Fragment() {

    //Todo: 전역변수 정의
    //Fixme:

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 퀴즈 출제 버튼
        button_makequiz_home.setOnClickListener {
            val intent = Intent(context, MakequizActivity::class.java)
            startActivity(intent)
        }

        // 주민 출제 퀴즈 버튼
        button_residentquiz_home.setOnClickListener {
            val intent = Intent(context, ResidentQuizActivity::class.java)
            startActivity(intent)
        }

        speech_bubble1.visibility = View.INVISIBLE
        speech_bubble2.visibility = View.INVISIBLE

        //loading our custom made animations
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)

        //fading our custom made animations
        val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)

        speech_bubble1.startAnimation(fadeIn)

        Handler().postDelayed({
            //Do something after 5000ms
            speech_bubble1.startAnimation(fadeOut)
            speech_bubble2.startAnimation(fadeIn)
        }, 5000)
    }
}