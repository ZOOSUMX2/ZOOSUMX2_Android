package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MailboxFirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mailbox_first)

        val button = findViewById<ImageButton>(R.id.imagebutton_back_mailbox_first)
        button.setOnClickListener {

            // back button 클릭 시 현재 activity 종료하고 이전 화면(우편함)으로 돌아감
            finish()
        }

    }
}