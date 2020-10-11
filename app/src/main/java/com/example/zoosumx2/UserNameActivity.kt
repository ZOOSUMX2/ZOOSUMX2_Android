package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user_name.*

class UserNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)

        //사용자 이름

        val nextButton = findViewById<Button>(R.id.userName_button_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, GetRegionActivity::class.java)
            intent.putExtra("user_name", userName_edit.text.toString())
            if (!TextUtils.isEmpty(userName_edit.getText())) {
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}