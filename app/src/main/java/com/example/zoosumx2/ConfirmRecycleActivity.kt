package com.example.zoosumx2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_confirm_recycle.*

class ConfirmRecycleActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_recycle)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // status bar 색상 변경
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendly_green)

        val backButton = findViewById<ImageButton>(R.id.confirm_recycle_back)
        backButton.setOnClickListener {
            finish()
        }

        val checkboxS1 = findViewById<CheckBox>(R.id.checkbox_step1)
        val textS1 = findViewById<TextView>(R.id.text_step1)
        val checkboxS2 = findViewById<CheckBox>(R.id.checkbox_step2)
        val textS2 = findViewById<TextView>(R.id.text_step2)
        val checkboxS3 = findViewById<CheckBox>(R.id.checkbox_step3)
        val textS3 = findViewById<TextView>(R.id.text_step3)
        val nextButton = findViewById<Button>(R.id.confirm_recycle_next)

        //초기 상태
        checkboxS1.isChecked = false
        textS1.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkboxS2.isChecked = false
        textS2.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkboxS3.isChecked = false
        textS3.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        nextButton.isSelected = false
        nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        //step1
        checkboxS1.setOnClickListener {
            if (!checkboxS2.isChecked && !checkboxS3.isChecked) {
                checkboxS1.isChecked = true
                textS1.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            if (checkboxS2.isChecked || checkboxS3.isChecked) {
                checkboxS1.isChecked = true
            }
        }

        //step2
        checkboxS2.setOnClickListener {
            if (checkboxS1.isChecked && !checkboxS3.isChecked) {
                checkboxS2.isChecked = true
                textS2.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
            checkboxS2.isChecked = checkboxS1.isChecked || checkboxS3.isChecked
        }

        //step3
        checkboxS3.setOnClickListener {
            if (checkboxS1.isChecked && checkboxS2.isChecked) {
                checkboxS3.isChecked = true
                textS3.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            } else {
                checkboxS3.isChecked = false
            }

            if (checkboxS1.isChecked && checkboxS2.isChecked && checkboxS3.isChecked) {
                nextButton.isSelected = true
                nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
        }


        nextButton.setOnClickListener {
            if (checkboxS1.isChecked && checkboxS2.isChecked && checkboxS3.isChecked) {
                val intent = Intent(this, PhotoActivity::class.java)
                intent.putExtra("missionTitle",confirm_title.text.toString())
                intent.putExtra("missionContent",confirm_ment_sub.text.toString())
                startActivity(intent)
            } else{
                nextButton.isSelected = false
            }
        }

    }




}