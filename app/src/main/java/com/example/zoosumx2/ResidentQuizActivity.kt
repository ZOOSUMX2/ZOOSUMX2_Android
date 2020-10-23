package com.example.zoosumx2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlin.random.Random

class ResidentQuizActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resident_quiz)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        var correctAns = findViewById<Button>(R.id.button_correct_answer_quiz)
        var wrongAns = findViewById<Button>(R.id.button_wrong_answer_quiz)
        val question = findViewById<TextView>(R.id.textview_question_resident_quiz)
        val header = findViewById<TextView>(R.id.textview_header_resident_quiz)

        //정답 문항의 자리 랜덤으로 교환
        val randomAnswerNum = Random.nextInt(2)
        if (randomAnswerNum == 0) {
            correctAns = findViewById(R.id.button_wrong_answer_quiz)
            wrongAns = findViewById(R.id.button_correct_answer_quiz)
        }

        fbFirestore?.collection("userQuiz")?.whereEqualTo("adopt", true)
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                for (snapshot in documentSnapshot) {
                    question.text = snapshot.getString("title").toString().replace("bb", "\n")
                    correctAns.text = snapshot.getString("correctAns").toString()
                    wrongAns.text = snapshot.getString("wrongAns").toString()

                }
            }

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
            //firestore의 mission 수행 여부 true로 변경
            val missionFlag = hashMapOf(
                "missionUserQuiz" to "true"
            )
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.set(missionFlag, SetOptions.merge())?.addOnSuccessListener { Log.d("Set WeekNumber to DB", "DocumentSnapshot successfully written!") }
                ?.addOnFailureListener { e -> Log.w("Set WeekNumber to DB", "Error writing document", e) }


            // 둘 중 하나의 답을 선택한 경우
            if (correctAns.isSelected || wrongAns.isSelected) {
                val checkAns: Boolean

                // 정답을 선택한 경우
                if (correctAns.isSelected) {
                    checkAns = true
                    header.text = "정답이에요!"
                    correctAns.setBackgroundResource(R.drawable.random_correct_correct_color)
                    correctAns.setTextColor(Color.WHITE)

                    // 정답 리워드 제공
                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(2))
                }

                // 오답을 선택한 경우
                else {
                    checkAns = false
                    header.text = "정답이... 아니에요"
                    wrongAns.setBackgroundResource(R.drawable.random_wrong_correct_color)
                    wrongAns.setTextColor(Color.WHITE)

                    // 오답 리워드 제공
                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(1))
                }

                submitAns.text = "리워드 확인" // 정답 확인 버튼을 리워드 확인 버튼으로 변경

                // 리워드 확인 버튼 클릭 이벤트
                submitAns.setOnClickListener {
                    val intent = Intent(this, GetRewardActivity::class.java)
                    if (checkAns) {
                        intent.putExtra("reward", 2)
                    } else {
                        intent.putExtra("reward", 1)
                    }

                    // 주민 출제 퀴즈 미션 완료 -> 경험치 10 증가
                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                            if (documentSnapshot == null) return@addSnapshotListener
                            val exp = documentSnapshot.data?.get("exp").toString().toInt()
                            if (exp < 1000) {
                                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                                    ?.update("exp", FieldValue.increment(10))
                            }
                        }

                    startActivity(intent)
                }
            }
            // 아무것도 선택하지 않은 경우 -> 정답 확인 버튼 비활성화
            else {
                Toast.makeText(applicationContext, "정답을 선택해주세요", Toast.LENGTH_LONG).show()
            }
        }

    }
}