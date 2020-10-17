package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

        val userName = findViewById<TextView>(R.id.textview_username_island_name)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                userName.text = documentSnapshot.data?.get("nickname").toString()
            }

        val nextButton = findViewById<Button>(R.id.islandName_button_next)
        nextButton.setOnClickListener {

            val islandName = findViewById<EditText>(R.id.edittext_islandName_edit)
            if (islandName.text.toString().trim().isNotEmpty()) {
                //intent.putExtra("user_info_count", 1.toString())

                userInfo.islandName = islandName.text.toString()

                // firestore에 사용자 정보 업데이트
                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                    ?.update("islandName", userInfo.islandName.toString())

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "섬 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}