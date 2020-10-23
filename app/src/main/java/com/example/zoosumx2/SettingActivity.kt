package com.example.zoosumx2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        fbFirestore = FirebaseFirestore.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val button = findViewById<ImageButton>(R.id.imagebutton_back_setting)
        button.setOnClickListener {

            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }

        //initializing firebase authentication object
        auth = FirebaseAuth.getInstance()

        // 로그아웃
        // Todo : 로그아웃 다이얼로그
        val logout = findViewById<LinearLayout>(R.id.linearlayout_logout_setting)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            // Todo : 재로그인 시에 예전에 로그인하였던 구글 계정으로 자동 로그인 되는 것 방지
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // 회원탈퇴
        // Todo : 회원탈퇴 다이얼로그
        val withdrawal = findViewById<LinearLayout>(R.id.linearlayout_withdrawal_setting)
        withdrawal.setOnClickListener {
            // firestore 내 사용자 정보 삭제
            fbFirestore?.collection("users")?.document(auth?.uid.toString())?.delete()

            // 구글 로그인 계정 삭제
            auth?.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "정상적으로 탈퇴되었습니다", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
        }

        // 문의하기
        val ask = findViewById<LinearLayout>(R.id.linearlayout_ask_setting)
        ask.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("aerimforest98@gmail.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "주섬주섬 문의사항")
            email.putExtra(Intent.EXTRA_TEXT, "문의사항을 자세하게 입력해주세요\n\n")
            startActivity(email)
        }

        // 개인정보 처리방침
        val policy = findViewById<LinearLayout>(R.id.linearlayout_policy_setting)
        policy.setOnClickListener {
            val url = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/3ad045e053a44d43b28b08880820bbe1")
            )
            startActivity(url)
        }

        // 서비스 이용약관
        val terms = findViewById<LinearLayout>(R.id.linearlayout_terms_setting)
        terms.setOnClickListener {
            val url = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.notion.so/2b9fb2ee9c1445de83447ebd5c4737d9")
            )
            startActivity(url)
        }
    }
}
