package com.example.zoosumx2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2)
        val fadeIn3 = AnimationUtils.loadAnimation(this, R.anim.fade_in3)

        val button1 = findViewById<ImageButton>(R.id.imagebutton_back_store)
        button1.setOnClickListener {

            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }

        // 포인트 클릭 시 나의 포인트 내역 페이지로 이동
        val myCoin = findViewById<TextView>(R.id.textview_mycoin_store)
        myCoin.setOnClickListener {
            val intent = Intent(this, PointActivity::class.java)
            startActivity(intent)
        }

        val on5 = findViewById<ImageButton>(R.id.imagebutton_onnuri5000_store)
        val yong10 = findViewById<ImageButton>(R.id.imagebutton_yongsan10000_store)
        val yong50 = findViewById<ImageButton>(R.id.imagebutton_yongsan50000_store)

        on5.visibility = View.INVISIBLE
        yong10.visibility = View.INVISIBLE
        yong50.visibility = View.INVISIBLE

        on5.startAnimation(fadeIn)
        yong10.startAnimation(fadeIn2)
        yong50.startAnimation(fadeIn3)

        // 온누리 오천원권 클릭시 웹페이지 연결
        on5.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }

        // 용산사랑상품권 만원권 클릭시 웹페이지 연결
        yong10.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }

        // 용산사랑상품권 오만원권 클릭시 웹페이지 연결
        yong50.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }

    }
}