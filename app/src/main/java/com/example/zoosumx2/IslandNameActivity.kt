package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_get_region.*

class IslandNameActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_island_name)

        val backButton = findViewById<ImageButton>(R.id.islandName_button_back)
        backButton.setOnClickListener {
            val intent = Intent(this, GetRegionActivity::class.java)
            startActivity(intent)
        }

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val userInfo = UserDTO()
        userInfo.uid = fbAuth?.uid.toString() // 로그인한 사용자 id 받아오기

        val islandName = findViewById<EditText>(R.id.edittext_islandName_edit)

        val nextButton = findViewById<Button>(R.id.islandName_button_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_info_count", 1.toString())

            userInfo.islandName = islandName.text.toString()

            // firestore에 사용자 정보 업데이트
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("islandName", userInfo.islandName.toString())

            startActivity(intent)
        }

        if (islandName.text.toString().trim().isNotEmpty()) {
            nextButton.isEnabled = true // 섬 이름 입력했을 시에만 다음 버튼 활성
        }

        if(intent.hasExtra("user_region")){
            region_edit.text = intent.getStringExtra("user_region")
            userInfo.addressRegion = region_edit.text.toString()

            // firestore에 사용자 정보 업데이트
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update("addressRegion", userInfo.addressRegion.toString())
        }

//        if(intent.hasExtra("user_name")){
//            islandName_user_name.text = intent.getStringExtra("user_name")
//        }

    }
}