package com.example.zoosumx2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_randomquiz_wrong.*

class RandomquizWrongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomquiz_wrong)

        val correct_answer = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrong_answer = findViewById<Button>(R.id.button_wrong_answer_quiz)

        correct_answer.isSelected=true
        correct_answer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        wrong_answer.isSelected=false
        wrong_answer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        val button_next = findViewById<Button>(R.id.random_quiz_wrong_next)
        button_next.setOnClickListener{
            val intent = Intent(this, GetRewardActivity::class.java)
            startActivity(intent)
        }

    }
}