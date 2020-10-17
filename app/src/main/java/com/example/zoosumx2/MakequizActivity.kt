package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MakequizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makequiz)

        val backButton = findViewById<ImageButton>(R.id.imagebutton_back_general_quiz)
        backButton.setOnClickListener {
            finish()
        }

        val makeQuizButton = findViewById<Button>(R.id.button_makequiz_button_makequiz)
        val question = findViewById<EditText>(R.id.edittext_question_makequiz)
        val correctExam = findViewById<EditText>(R.id.edittext_correctans_makequiz)
        val wrongExam = findViewById<EditText>(R.id.edittext_wrongans_makequiz)

        // 퀴즈 출제 버튼 클릭 이벤트
        makeQuizButton.setOnClickListener {

            // 문제 입력, 정답/오답 선지 모두 입력 시에만 퀴즈 출제 버튼 활성화
            if (question.text.toString().trim().isNotEmpty()) {
                if (correctExam.text.toString().trim().isNotEmpty()) {
                    if (wrongExam.text.toString().trim().isNotEmpty()) {
                        // 모두 입력한 경우 리워드 액티비티로 이동
                        val intent = Intent(this, GetRewardActivity::class.java)
                        startActivity(intent)

                        // Todo : 사용자가 작성한 정보 저장

                    } else {
                        Toast.makeText(applicationContext, "오답이 될 선지를 입력해주세요", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "정답이 될 선지를 입력해주세요", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "문제를 입력해주세요", Toast.LENGTH_SHORT).show()
            }

        }
    }
}