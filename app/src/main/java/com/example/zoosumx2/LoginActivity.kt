package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val kakaoButton = findViewById<Button>(R.id.button_kakao_login)
        kakaoButton.setOnClickListener {
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
        }
    }
}