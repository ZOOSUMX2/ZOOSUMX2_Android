package com.example.zoosumx2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_random_quiz.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class RandomQuizActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_quiz)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageButton>(R.id.random_quiz_back)
        backButton.setOnClickListener {
            finish()
        }

        var correctAnswer = findViewById<Button>(R.id.button_correct_answer_quiz)
        var wrongAnswer = findViewById<Button>(R.id.button_wrong_answer_quiz)
        val nextButton = findViewById<Button>(R.id.random_quiz_next)

        //정답 문항의 자리 랜덤으로 교환
        val randomAnswerNum = Random.nextInt(2)
        if (randomAnswerNum == 0) {
            correctAnswer = findViewById(R.id.button_wrong_answer_quiz)
            wrongAnswer = findViewById(R.id.button_correct_answer_quiz)
        }

        //DB에서 문제 및 문항 가져오기
        //해당 달의 숫자가 이름인 문서에 저장한 문제를 가져옴
        //문제가 길어질 경우 줄 바꿈은 DB 저장 시 bb를 사용함
        val weekly: String = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))
        fbFirestore?.collection("senseQuiz")?.document(weekly)
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                random_quiz_main_ment?.text =
                    documentSnapshot.data?.get("title").toString().replace("bb", "\n")
                correctAnswer?.text = documentSnapshot.data?.get("option1").toString()
                wrongAnswer?.text = documentSnapshot.data?.get("option2").toString()
            }


        correctAnswer.setOnClickListener {
            if (wrongAnswer.isSelected) {
                wrongAnswer.isSelected = false
                wrongAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            correctAnswer.isSelected = true
            correctAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        }

        wrongAnswer.setOnClickListener {
            if (correctAnswer.isSelected) {
                correctAnswer.isSelected = false
                correctAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            wrongAnswer.isSelected = true
            wrongAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        }

        var answerChecked = false

        nextButton.setOnClickListener {
            random_quiz_next.text = "리워드 확인"

            //firestore의 mission 수행 여부 true로 변경
            val missionFlag = hashMapOf(
                "missionSenseQuiz" to "true"
            )
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.set(missionFlag, SetOptions.merge())?.addOnSuccessListener { Log.d("Set WeekNumber to DB", "DocumentSnapshot successfully written!") }
                ?.addOnFailureListener { e -> Log.w("Set WeekNumber to DB", "Error writing document", e) }

            if (correctAnswer.isSelected || wrongAnswer.isSelected) {
                if (correctAnswer.isSelected) {
                    answerChecked = true
                    random_quiz_main_ment?.text = "정답이에요!"
                    random_quiz_image.setImageResource(R.drawable.you_right)
                    random_quiz_sub_ment?.text = "페트병 버리는 방법 잘 알고 계시네요!"
                    correctAnswer.setBackgroundResource(R.drawable.random_correct_correct_color)
                    correctAnswer.setTextColor(Color.WHITE)

                    wrongAnswer.setBackgroundResource(R.drawable.random_correct_wrong_color)
                    wrongAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(2))

                } else {
                    answerChecked = false
                    random_quiz_main_ment?.text = "정답이...아니에요"
                    random_quiz_image.setImageResource(R.drawable.you_wrong)
                    random_quiz_sub_ment?.text = "다 마신 페트병은 이렇게 버려야 해요!"
                    wrongAnswer.setBackgroundResource(R.drawable.random_wrong_correct_color)
                    wrongAnswer.setTextColor(Color.WHITE)

                    correctAnswer.setBackgroundResource(R.drawable.random_wrong_wrong_color)
                    correctAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(1))

                }
            }
            // 아무것도 선택하지 않은 경우 -> 정답 확인 버튼 비활성화
            else {
                Toast.makeText(applicationContext, "정답을 선택해주세요", Toast.LENGTH_LONG).show()
            }

            nextButton.setOnClickListener {

                val intent = Intent(this, GetRewardActivity::class.java)

                if (answerChecked) {
                    intent.putExtra("reward", 2)
                } else {
                    intent.putExtra("reward", 1)
                }

                // 상식 퀴즈 미션 완료 -> 경험치 10 증가
//                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
//                    ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
//                        if (documentSnapshot == null) return@addSnapshotListener
//                        val exp = documentSnapshot.data?.get("exp").toString().toInt()
//                        if (exp < 1000) {
//                            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
//                                ?.update("exp", FieldValue.increment(10))
//                        }
//                    }


                startActivity(intent)
            }
        }
    }
}