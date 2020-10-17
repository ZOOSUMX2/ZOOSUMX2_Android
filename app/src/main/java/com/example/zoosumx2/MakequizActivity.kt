package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class MakequizActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makequiz)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val userInfo = UserDTO()
        userInfo.uid = fbAuth?.uid.toString() // 로그인한 사용자 id 받아오기


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

                        // 리워드 제공
                        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                            ?.update("rewardPoint", FieldValue.increment(3))

                        // firestore에 사용자 정보 업데이트
                        // 서버 타임스탬프
                        val updates = hashMapOf<String, Any>(
                            "creationTimestamp" to FieldValue.serverTimestamp()
                        )

                        // Todo : creator 정보 userInfo.nickname.toString()에서 안불러와짐

                        val userQuizInfo = hashMapOf(
                            "title" to question.text.toString(),
                            "option1" to correctExam.text.toString(),
                            "option2" to wrongExam.text.toString(),
                            "creator" to userInfo.nickname.toString(),
                            "creationTimestamp" to updates
                        )

                        fbFirestore?.collection("userQuiz")?.add(userQuizInfo) // document id 자동 생성

                        // 모두 입력한 경우 리워드 액티비티로 이동
                        val intent = Intent(this, GetRewardActivity::class.java)
                        startActivity(intent)

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