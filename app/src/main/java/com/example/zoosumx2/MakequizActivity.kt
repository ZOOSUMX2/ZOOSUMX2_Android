package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MakequizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makequiz)

        val button = findViewById<ImageButton>(R.id.imagebutton_back_general_quiz)
        button.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }

        //Todo : 퀴즈 출제 버튼 클릭 이벤트
    }
}