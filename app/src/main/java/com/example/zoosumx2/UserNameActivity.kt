package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_name.*

class UserNameActivity : AppCompatActivity() {

    var fbAuth : FirebaseAuth? = null
    var fbFirestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val userInfo = UserDTO()
        userInfo.uid = fbAuth?.uid.toString() // 로그인한 사용자 id 받아오기
        //userInfo.nickname = userName_edit.text.toString()

        //사용자 이름

        val nextButton = findViewById<Button>(R.id.userName_button_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, GetRegionActivity::class.java)

            // 다음 액티비티에 사용자 이름 넘겨줌
            intent.putExtra("user_name", userName_edit.text.toString())
            userInfo.nickname = userName_edit.text.toString()

            // firestore에 사용자 정보 업로
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.set(userInfo)


            if (!TextUtils.isEmpty(userName_edit.getText())) {
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}