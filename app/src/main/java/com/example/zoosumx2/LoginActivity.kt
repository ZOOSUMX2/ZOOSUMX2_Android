package com.example.zoosumx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private lateinit var auth: FirebaseAuth
    private var googleSignInClient: GoogleSignInClient? = null
    private val GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        //email login
        email_login_button?.setOnClickListener {
            emailLogin()
        }

        //google login
        button_google_login.setOnClickListener {
            googleLogin()
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    // 로그인 한 적이 있는지 확인
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        // 이미 로그인 한 사용자인 경우
        if (account != null) {
            moveMainPage(auth.currentUser) // 바로 MainActivity로 이동
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

            try {
                val account = result.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {

            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            // 결과값 받음
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // log in
                    //moveMainPage(task.result?.user)
                    moveUserNamePage(auth.currentUser)
                } else {
                    // 에러 메세지 보여줌
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        // user가 있는 경우(로그인 한 적이 있는 경우)
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun moveUserNamePage(user: FirebaseUser?) {
        // user가 있는 경우(로그인 한 경우)
        if (user != null) {
            val intent = Intent(this, UserNameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createAndLoginEmail() {
        auth.createUserWithEmailAndPassword(
            email_login_edittext?.text.toString(),
            password_login_edittext?.text.toString()
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //아이디 생성이 성공했을 경우
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    moveUserNamePage(auth.currentUser)
                } else if (task.exception?.message.isNullOrEmpty()) {
                    //회원가입 중 에러가 발생했을 경우
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                } else {
                    signinEmail()
                }
            }
    }

    //이메일과 패스워드가 올바르게 입력되었는지 확인
    private fun emailLogin() {
        if (email_login_edittext?.text.toString()
                .isEmpty() || password_login_edittext?.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            createAndLoginEmail()
        }
    }

    //이메일로 로그인 메소드
    private fun signinEmail() {
        auth.signInWithEmailAndPassword(
            email_login_edittext?.text.toString(),
            password_login_edittext?.text.toString()
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //로그인 성공 및 다음 페이지 호출
                    moveMainPage(auth.currentUser)
                } else {
                    //로그인 실패
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}