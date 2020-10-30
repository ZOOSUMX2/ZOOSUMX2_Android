package com.zoosumzoosum.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val header = findViewById<TextView>(R.id.textview_header_mailbox_first)
        val date = findViewById<TextView>(R.id.textview_issue_date_news)
        val goOffice = findViewById<TextView>(R.id.textview_gooffice_news)
        val content = findViewById<TextView>(R.id.textview_context_mailbox_first)

        header.text = intent.getStringExtra("header").toString().replace("bb", "\n")
        date.text = intent.getStringExtra("date").toString()
        goOffice.text = intent.getStringExtra("guOffice").toString()
        content.text = intent.getStringExtra("content").toString().replace("bb", "\n")

        val button = findViewById<ImageButton>(R.id.imagebutton_back_mailbox_first)
        button.setOnClickListener {

            // back button 클릭 시 현재 activity 종료하고 이전 화면(우편함)으로 돌아감
            finish()
        }

    }
}