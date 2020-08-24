package com.example.zoosumx2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Resident_quizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_quiz)

        val button = findViewById<Button>(R.id.correct_answer)
        button.setOnClickListener {
            fun onClick(view: View) {
                button.setBackgroundColor(R.drawable.item_color)
            }
        }
    }
}