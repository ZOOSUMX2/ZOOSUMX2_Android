package com.example.zoosumx2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_random_quiz.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class RandomQuizActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_quiz)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageButton>(R.id.random_quiz_back)
        backButton.setOnClickListener{
            finish()
        }

        var correct_answer = findViewById<Button>(R.id.button_correct_answer_quiz)
        var wrong_answer = findViewById<Button>(R.id.button_wrong_answer_quiz)
        val nextButton = findViewById<Button>(R.id.random_quiz_next)

        //정답 문항의 자리 랜덤으로 교환
        val random_answer_num = Random.nextInt(2)
        if(random_answer_num==0){
            correct_answer = findViewById<Button>(R.id.button_wrong_answer_quiz)
            wrong_answer = findViewById<Button>(R.id.button_correct_answer_quiz)
        }


        //DB에서 문제 및 문항 가져오기
        //해당 달의 숫자가 이름인 문서에 저장한 문제를 가져옴
        //문제가 길어질 경우 줄 바꿈은 DB 저장 시 bb를 사용함
        val weekly: String = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))
        fbFirestore?.collection("senseQuiz")?.document(weekly)
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                random_quiz_main_ment?.text = documentSnapshot.data?.get("title").toString().replace("bb","\n")
                correct_answer?.text = documentSnapshot.data?.get("option1").toString()
                wrong_answer?.text = documentSnapshot.data?.get("option2").toString()
            }


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

        var answer_checked: Boolean = false

        nextButton.setOnClickListener{
            random_quiz_next.text="리워드 확인"

            if(correct_answer.isSelected||wrong_answer.isSelected){
                if(correct_answer.isSelected){
                    answer_checked = true
                    random_quiz_main_ment.text="정답이에요!"
                    random_quiz_image.setImageResource(R.drawable.you_right)
                    random_quiz_sub_ment.text="페트병 버리는 방법 잘 알고 계시네요!"
                    correct_answer.setBackgroundResource(R.drawable.random_correct_correct_color)
                    correct_answer.setTextColor(Color.WHITE)

                    wrong_answer.setBackgroundResource(R.drawable.random_correct_wrong_color)
                    wrong_answer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(2))

                }
                else{
                    answer_checked = false
                    random_quiz_main_ment.text="정답이...아니에요"
                    random_quiz_image.setImageResource(R.drawable.you_wrong)
                    random_quiz_sub_ment.text="다 마신 페트병은 이렇게 버려야 해요!"
                    wrong_answer.setBackgroundResource(R.drawable.random_wrong_correct_color)
                    wrong_answer.setTextColor(Color.WHITE)

                    correct_answer.setBackgroundResource(R.drawable.random_wrong_wrong_color)
                    correct_answer.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.update("rewardPoint", FieldValue.increment(1))

                }
            }

            nextButton.setOnClickListener {
                val intent = Intent(this, GetRewardActivity::class.java)

                if(answer_checked){
                    intent.putExtra("reward", 2)
                }
                else{
                    intent.putExtra("reward", 1)
                }
                startActivity(intent)
            }
        }
    }
}