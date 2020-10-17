package com.example.zoosumx2

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetRewardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_reward)

        val finalPoint = findViewById<TextView>(R.id.textview_finalpoint_getreward)
        val getReward = findViewById<TextView>(R.id.textview_reward_get_reward)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        // 리워드 포인트 애니메이션
        fun startAnimation() {
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

        startAnimation()
        finalPoint.startAnimation(fadeIn)

        val nextButton = findViewById<Button>(R.id.get_reward_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}