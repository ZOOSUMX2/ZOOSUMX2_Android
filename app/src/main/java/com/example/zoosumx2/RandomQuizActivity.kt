package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RandomQuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_quiz)

        val backButton = findViewById<ImageButton>(R.id.random_quiz_back)
        backButton.setOnClickListener{
            finish()
        }

        val correct_answer = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrong_answer = findViewById<Button>(R.id.button_wrong_answer_quiz)
        val nextButton = findViewById<Button>(R.id.random_quiz_next)

        correct_answer.setOnClickListener{
            if(wrong_answer.isSelected){
                wrong_answer.isSelected=false
                wrong_answer.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
                correct_answer.isSelected=true
                correct_answer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        }

        wrong_answer.setOnClickListener {
            if (correct_answer.isSelected) {
                correct_answer.isSelected = false
                correct_answer.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            wrong_answer.isSelected = true
            wrong_answer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        }

        nextButton.setOnClickListener{
            if(correct_answer.isSelected||wrong_answer.isSelected){
                if(correct_answer.isSelected){
                    val intent = Intent(this, RandomquizCorrectActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, RandomquizWrongActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}