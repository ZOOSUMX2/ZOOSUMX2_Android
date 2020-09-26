package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_get_region.*
import kotlinx.android.synthetic.main.activity_island_name.*

class IslandNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_island_name)

        if(intent.hasExtra("user_name")){
            islandName_user_name.text = intent.getStringExtra("user_name")
        }

        val nextButton = findViewById<Button>(R.id.islandName_button_next)
        nextButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_info_count",1.toString())
            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.islandName_button_back)
        backButton.setOnClickListener{
            val intent = Intent(this, GetRegionActivity::class.java)
            startActivity(intent)
        }

    }
}