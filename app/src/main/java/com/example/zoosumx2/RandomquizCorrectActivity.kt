package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RandomquizCorrectActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_randomquiz_correct)

        val correct_answer = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrong_answer = findViewById<Button>(R.id.button_wrong_answer_quiz)

        correct_answer.isSelected=true
        correct_answer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        wrong_answer.isSelected=false
        wrong_answer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        val button_next = findViewById<Button>(R.id.random_quiz_correct_next)
        button_next.setOnClickListener{
            val intent = Intent(this, GetRewardActivity::class.java)

            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("rewardPoint", FieldValue.increment(2))

            intent.putExtra("reward", 2)

            startActivity(intent)
        }
    }
}