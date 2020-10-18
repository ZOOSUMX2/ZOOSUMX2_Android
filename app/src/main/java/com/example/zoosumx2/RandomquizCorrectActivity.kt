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

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // 정답 리워드 제공
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.update("rewardPoint", FieldValue.increment(2))

        val correctAnswer = findViewById<Button>(R.id.button_correct_answer_quiz)
        val wrongAnswer = findViewById<Button>(R.id.button_wrong_answer_quiz)

        correctAnswer.isSelected = true
        correctAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        wrongAnswer.isSelected = false
        wrongAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        val buttonNext = findViewById<Button>(R.id.random_quiz_correct_next)
        buttonNext.setOnClickListener {
            val intent = Intent(this, GetRewardActivity::class.java)

            // 상식 퀴즈 미션 완료 -> 경험치 10 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("exp", FieldValue.increment(10))

            intent.putExtra("reward", 2)
            startActivity(intent)
        }
    }
}