package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_get_region.*


class GetRegionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_region)

        if(intent.hasExtra("user_name")){
            show_user_name.text = intent.getStringExtra("user_name")
        }

        if(intent.hasExtra("user_region")){
            region_edit.text = intent.getStringExtra("user_region")
        }

        val nextButton = findViewById<Button>(R.id.region_button_next)
        nextButton.setOnClickListener{
            val intent = Intent(this, IslandNameActivity::class.java)
            intent.putExtra("user_name",getIntent())
            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.region_button_back)
        backButton.setOnClickListener{
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
        }

        //bottom sheet dialog
        region_edit.setOnClickListener{
            var bottomFragment = BottomFragment()
            bottomFragment.show(supportFragmentManager,"TAG")
        }
    }




}