package com.example.zoosumx2

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.dialog.LevelUpDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class GetRewardActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_reward)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()


        val finalPoint = findViewById<LinearLayout>(R.id.linearlayout_final_point_get_reward)
        val finalReward = findViewById<TextView>(R.id.textview_final_reward_get_reward)
        val getReward = findViewById<TextView>(R.id.textview_reward_get_reward)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)


        // 리워드 포인트 애니메이션
        fun rewardAnimation() {
            val animator = ValueAnimator.ofInt(
                intent.getIntExtra("reward", 0) + 8,
                intent.getIntExtra("reward", 0)
            )
            animator.duration = 1200 // 1 seconds
            animator.addUpdateListener { animation ->
                getReward.text = animation.animatedValue.toString()
            }
            animator.start()
        }

        // 경험치 제공
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.update("exp", FieldValue.increment(10))
        // 미션 완료 개수 1 증가
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.update("mission", FieldValue.increment(1))

        // 나의 최종 포인트 띄워줌
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                finalReward.text = documentSnapshot.data?.get("rewardPoint").toString()
                val exp = documentSnapshot.data?.get("exp").toString().toInt()
                val currentLevel = documentSnapshot.data?.get("level").toString().toInt()

                // 레벨 계산
                val newLevel = when {
                    (exp in 0..99) -> 1
                    (exp in 100..299) -> 2
                    (exp in 300..599) -> 3
                    (exp in 600..999) -> 4
                    else -> 5
                }

                rewardAnimation()
                finalPoint.startAnimation(fadeIn)

                val nextButton = findViewById<Button>(R.id.get_reward_next)
                nextButton.setOnClickListener {

                    if (currentLevel != newLevel) {
                        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                            ?.update("level", newLevel)

                        // 레벨 업 다이얼로그
                        //     val dlg = LevelUpDialog(this)
                        //     dlg.start(this)
                    }
                    //val dlg = LevelUpDialog(this)
                    //dlg.start()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}