package com.example.zoosumx2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat

class ResidentQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_quiz)

        val correctAns = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrongAns = findViewById<Button>(R.id.button_wrong_answer_quiz)

        correctAns.setOnClickListener {
            if (wrongAns.isSelected) {
                wrongAns.isSelected = false
                wrongAns.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            correctAns.isSelected = true
            correctAns.setTextColor(Color.WHITE)
        }

        wrongAns.setOnClickListener {
            if (correctAns.isSelected) {
                correctAns.isSelected = false
                correctAns.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            wrongAns.isSelected = true
            wrongAns.setTextColor((Color.WHITE))
        }

        // 이전 버튼 클릭 이벤트
        val backButton = findViewById<ImageButton>(R.id.imagebutton_back_resident_quiz)
        backButton.setOnClickListener {
            finish()
        }

        // 정답확인 버튼 클릭 이벤트
        val submitAns = findViewById<Button>(R.id.button_check_answer_resident_quiz)
        submitAns.setOnClickListener {

            // 정답을 선택한 경우
            if(correctAns.isSelected) {

            }
            // 오답을 선택한 경우
            else {
                val intent = Intent(this, WrongAnsActivity::class.java)
                startActivity(intent)
            }
        }

    }
}