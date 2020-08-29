package com.example.zoosumx2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class StoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val button1 = findViewById<ImageButton>(R.id.imagebutton_back_store)
        button1.setOnClickListener {

            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }

        // 온누리 오천원권 클릭시 웹페이지 연결
        val button2 = findViewById<ImageButton>(R.id.imagebutton_onnuri5000_store)
        button2.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }

        // 용산사랑상품권 만원권 클릭시 웹페이지 연결
        val button3 = findViewById<ImageButton>(R.id.imagebutton_yongsan10000_store)
        button3.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }

        // 용산사랑상품권 오만원권 클릭시 웹페이지 연결
        val button4 = findViewById<ImageButton>(R.id.imagebutton_yongsan50000_store)
        button4.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zoosum.site/"))
            startActivity(url)
        }
    }
}