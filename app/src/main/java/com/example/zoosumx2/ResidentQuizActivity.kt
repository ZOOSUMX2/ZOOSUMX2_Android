package com.example.zoosumx2

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
                wrongAns.setTextColor(Color.BLACK)
            }
            correctAns.isSelected = true
            correctAns.setTextColor(ContextCompat.getColor(this, R.color.friendly_green))
        }

        wrongAns.setOnClickListener {
            if (correctAns.isSelected) {
                correctAns.isSelected = false
                correctAns.setTextColor(ContextCompat.getColor(this, R.color.friendly_green))
            }
            wrongAns.isSelected = true
            wrongAns.setTextColor((Color.WHITE))
        }

        val backButton = findViewById<ImageButton>(R.id.imagebutton_back_resident_quiz)
        backButton.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            finish()
        }

        val submitAns = findViewById<Button>(R.id.button_check_answer_resident_quiz)
        submitAns.setOnClickListener {

            // 정답을 선택한 경우
            if(correctAns.isSelected) {

            }
            // 오답을 선택한 경우
            else {

            }
        }

    }
}