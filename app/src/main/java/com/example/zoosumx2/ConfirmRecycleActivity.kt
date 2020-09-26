package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_confirm_recycle.*
import kotlinx.android.synthetic.main.fragment_mypage.*

class ConfirmRecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_recycle)

        val backButton = findViewById<ImageButton>(R.id.confirm_recycle_back)
        backButton.setOnClickListener{
            finish()
        }

        val checkbox_s1 = findViewById<CheckBox>(R.id.checkbox_step1)
        val text_s1 =  findViewById<TextView>(R.id.text_step1)
        val checkbox_s2 = findViewById<CheckBox>(R.id.checkbox_step2)
        val text_s2 =  findViewById<TextView>(R.id.text_step2)
        val checkbox_s3 = findViewById<CheckBox>(R.id.checkbox_step3)
        val text_s3 =  findViewById<TextView>(R.id.text_step3)
        val nextButton = findViewById<Button>(R.id.confirm_recycle_next)

        //초기 상태
        checkbox_s1.isChecked = false
        text_s1.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkbox_s2.isChecked = false
        text_s2.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkbox_s3.isChecked = false
        text_s3.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        nextButton.isSelected = false
        nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        //step1
        checkbox_s1.setOnClickListener {
            if (!checkbox_s2.isChecked && !checkbox_s3.isChecked) {
                checkbox_s1.isChecked = true
                text_s1.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            if(checkbox_s2.isChecked||checkbox_s3.isChecked){
                checkbox_s1.isChecked=true
            }
        }

        //step2
        checkbox_s2.setOnClickListener {
            if(checkbox_s1.isChecked&&!checkbox_s3.isChecked){
                checkbox_s2.isChecked = true
                text_s2.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            checkbox_s2.isChecked = checkbox_s1.isChecked||checkbox_s3.isChecked
        }

        //step3
        checkbox_s3.setOnClickListener {
            if(checkbox_s1.isChecked&&checkbox_s2.isChecked){
                checkbox_s3.isChecked = true
                text_s3.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            else{
                checkbox_s3.isChecked = false
            }

            if(checkbox_s1.isChecked&&checkbox_s2.isChecked&&checkbox_s3.isChecked){
                nextButton.isSelected = true
                nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
        }


        nextButton.setOnClickListener {
            if(checkbox_s1.isChecked&&checkbox_s2.isChecked&&checkbox_s3.isChecked){
                val intent = Intent(this, PhotoActivity::class.java)
                startActivity(intent)
            }
            else{
                nextButton.isSelected = false
            }
        }

    }




}