package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_get_region.*

class GetRegionActivity : AppCompatActivity() {

    var fbAuth : FirebaseAuth? = null
    var fbFirestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_region)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val userInfo = UserDTO()
        val userName = findViewById<TextView>(R.id.textview_username_get_region)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                userName.text = documentSnapshot.data?.get("nickname").toString()
            }

        val nextButton = findViewById<Button>(R.id.region_button_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, IslandNameActivity::class.java)
            startActivity(intent)
        }

        val backButton = findViewById<ImageButton>(R.id.region_button_back)
        backButton.setOnClickListener {
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
        }

        if(intent.hasExtra("user_region")){
            region_edit.text = intent.getStringExtra("user_region")
            userInfo.addressRegion = region_edit.text.toString()

            // firestore에 사용자 정보 업데이트
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update("addressRegion", userInfo.addressRegion.toString())
        }

        //bottom sheet dialog
        region_edit.setOnClickListener{
            val bottomFragment = BottomFragment()
            bottomFragment.show(supportFragmentManager,"TAG")
        }
    }
}