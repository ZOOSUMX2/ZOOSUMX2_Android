package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class ResidentQuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_quiz)

        val button1 = findViewById<Button>(R.id.correct_answer)
        button1.setOnClickListener {
//            fun onClick(view: View) {
//                button1.setBackgroundColor(R.drawable.item_color)
//            }
        }

        val button2 = findViewById<ImageButton>(R.id.imagebutton_back_resident_quiz)
        button2.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }

        //Todo : 정답확인 버튼 클릭 이벤트

    }
}