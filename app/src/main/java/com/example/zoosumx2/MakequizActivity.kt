package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MakequizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makequiz)

        val backButton = findViewById<ImageButton>(R.id.imagebutton_back_general_quiz)
        backButton.setOnClickListener{
            finish()
        }

        val makeQuizButton = findViewById<Button>(R.id.button_makequiz_button_makequiz)
        makeQuizButton.setOnClickListener{
            finish()
        }
    }
}