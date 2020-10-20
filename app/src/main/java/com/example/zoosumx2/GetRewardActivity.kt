package com.example.zoosumx2

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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
                intent.getIntExtra("reward", 0) + 4,
                intent.getIntExtra("reward", 0)
            )
            animator.duration = 1200 // 1 seconds
            animator.addUpdateListener { animation ->
                getReward.text = animation.animatedValue.toString()
            }
            animator.start()
        }

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                finalReward.text = documentSnapshot.data?.get("rewardPoint").toString()
            }

        rewardAnimation()
        finalPoint.startAnimation(fadeIn)

        val nextButton = findViewById<Button>(R.id.get_reward_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}