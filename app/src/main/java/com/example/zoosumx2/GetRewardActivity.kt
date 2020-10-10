package com.example.zoosumx2

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
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        finalPoint.startAnimation(fadeIn)

        val nextButton = findViewById<Button>(R.id.get_reward_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}