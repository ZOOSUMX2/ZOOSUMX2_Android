package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    var googleSignInClient: GoogleSignInClient? = null
    val GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button_google_login.setOnClickListener {
            googleLogin()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 카카오 로그인 기능 구현 후 제거할 부분
        val kakaoButton = findViewById<Button>(R.id.button_kakao_login)
        kakaoButton.setOnClickListener {
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
        }
    }

    // 로그인 한 적이 있는지 확인
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        // 이미 로그인 한 사용자인 경우
        if (account != null) {
            moveMainPage(auth?.currentUser) // 바로 MainActivity로 이동
        }
    }

    fun googleLogin() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN_CODE) {
            // 구글에서 넘겨주는 로그인 결과 값 받아오기
            //val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val result = GoogleSignIn.getSignedInAccountFromIntent(data)

//            if (result != null) {
//                if(result.isSuccess) {
//                    val account = result.signInAccount
//                    if (account != null) {
//                        firebaseAuthWithGoogle(account)
//                    }
//                }
//            }

            try {
                val account = result.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {

            }
        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
            // 결과값 받음
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // log in
                    moveUserNamePage(task.result?.user)
                } else {
                    // 에러 메세지 보여줌
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    fun moveMainPage(user: FirebaseUser?) {
        // user가 있는 경우(로그인 한 경우)
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun moveUserNamePage(user: FirebaseUser?) {
        // user가 있는 경우(로그인 한 경우)
        if (user != null) {
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}