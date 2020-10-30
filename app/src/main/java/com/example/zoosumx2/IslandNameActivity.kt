package com.example.zoosumx2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.Calendar.WEEK_OF_YEAR

class IslandNameActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    // 백 버튼 금지
    override fun onBackPressed() {
    }

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

        //사용자가 처음 가입할 당시가 몇 주차인지 DB에 저장
        val today: Calendar = Calendar.getInstance()
        val todayWeek:Int = today.get(WEEK_OF_YEAR)

        val weekData = hashMapOf(
            "creationTimestamp" to todayWeek,
            "missionMakingQuiz" to "false", //첫 가입 시 미션 수행 여부 전부 false로 초기화
            "missionRecycle" to "false",
            "missionSenseQuiz" to "false",
            "missionUserQuiz" to "false"
        )
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.set(weekData)
//            ?.addOnSuccessListener { Log.d("Set WeekNumber to DB", "DocumentSnapshot successfully written!") }
//            ?.addOnFailureListener { e -> Log.w("Set WeekNumber to DB", "Error writing document", e) }


        val userInfo = UserDTO()
        userInfo.uid = fbAuth?.uid.toString() // 로그인한 사용자 id 받아오기

        val userName = findViewById<TextView>(R.id.textview_username_island_name)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                userName.text = documentSnapshot.data?.get("nickname").toString()
            }

        fun closeKeyboard() {
            val view = this.currentFocus
            if (view != null) {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        val nextButton = findViewById<Button>(R.id.islandName_button_next)
        nextButton.setOnClickListener {

            val islandName = findViewById<EditText>(R.id.edittext_islandName_edit)
            if (islandName.text.toString().trim().isNotEmpty()) {
                closeKeyboard() // 키보드 내려가게
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