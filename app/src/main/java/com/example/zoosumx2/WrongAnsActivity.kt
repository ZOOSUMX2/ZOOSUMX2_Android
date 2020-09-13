package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WrongAnsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_ans)

        // 확인 버튼 클릭 이벤트
        val okButton = findViewById<Button>(R.id.button_ok_wrong_ans)
        okButton.setOnClickListener {

            // 메인 화면으로 돌아감
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}