package com.zoosumzoosum.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_point.*

class PointActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val myCoin = findViewById<TextView>(R.id.textview_mypoint_point)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                myCoin.text = documentSnapshot.data?.get("rewardPoint").toString()
            }

        // Todo : 현재는 하드코딩, 이후에 데이터 받아와서 처리하는것으로 수정
        val pointDetailsList = arrayListOf<PointDetailsData>(
            PointDetailsData("주민 출제 퀴즈 완료", 1, "2020.08.31", "오후 2:43"),
            PointDetailsData("상식퀴즈 완료", 3, "2020.08.31", "오후 2:38"),
            PointDetailsData("상식퀴즈 완료", 1, "2020.08.30", "오후 1:43"),
            PointDetailsData("출석체크", 1, "2020.08.30", "오 2:43"),
            PointDetailsData("출석체크", 1, "2020.08.29", "오후 2:43"),
            PointDetailsData("퀴즈 채택", 10, "2020.07.31", "오후 2:43"),
            PointDetailsData("출석체크", 1, "2020.07.21", "오후 2:43"),
            PointDetailsData("출석체크", 1, "2020.07.15", "오후 2:43"),
            PointDetailsData("출석체크", 1, "2020.07.14", "오후 2:43"),
            PointDetailsData("출석체크", 1, "2020.07.13", "오후 2:43"),
            PointDetailsData("주민 출제 퀴즈 완료", 3, "2020.07.13", "오후 2:43")
        )

        val pointAdapter = PointAdapter(this, pointDetailsList)
        recyclerview_point_mypage.adapter = pointAdapter

        val lm = LinearLayoutManager(this)
        recyclerview_point_mypage.layoutManager = lm
        recyclerview_point_mypage.setHasFixedSize(true)

        val button = findViewById<ImageButton>(R.id.imagebutton_back_point_details)
        button.setOnClickListener {
            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }
    }
}