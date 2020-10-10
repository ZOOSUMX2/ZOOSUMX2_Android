package com.example.zoosumx2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class ResidentQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_quiz)

        val correctAns = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrongAns = findViewById<Button>(R.id.button_wrong_answer_quiz)
        val header = findViewById<TextView>(R.id.textview_header_resident_quiz)

        // 선지 둘 중에 하나만 선택되도록
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

            if (correctAns.isSelected || wrongAns.isSelected) {
                // 정답을 선택한 경우
                if (correctAns.isSelected) {
                    header.text = "정답이에요!"
                    correctAns.setBackgroundColor(ContextCompat.getColor(this, R.color.lively_blue))
                }
                // 오답을 선택한 경우
                else {
//                val intent = Intent(this, WrongAnsActivity::class.java)
//                startActivity(intent)
                    header.text = "정답이... 아니에요"
                    wrongAns.setBackgroundColor(ContextCompat.getColor(this, R.color.joyful_orange))
                }

                submitAns.text = "리워드 확인"

                // 리워드 확인 버튼 클릭 이벤트
                submitAns.setOnClickListener {
                    val intent = Intent(this, GetRewardActivity::class.java)
                    startActivity(intent)
                }
            }
            // 아무것도 선택하지 않은 경우 -> 정답 확인 버튼 비활성화
            else {
                submitAns.isEnabled = false
                Toast.makeText(applicationContext, "정답을 선택해주세요", Toast.LENGTH_LONG).show()
            }
        }

    }
}