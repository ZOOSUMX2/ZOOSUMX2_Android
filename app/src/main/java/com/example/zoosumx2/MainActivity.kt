package com.example.zoosumx2

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.dialog.ApproveRejectedDialog
import com.example.zoosumx2.dialog.ApproveToRewardDialog
import com.example.zoosumx2.dialog.ConfirmRecycleDialog
import com.example.zoosumx2.menu.MailboxFragment
import com.example.zoosumx2.menu.MypageFragment
import com.example.zoosumx2.menu.PagerHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // 재활용 인증 요청이 와있을 경우 다이얼로그를 출력(다이얼로그 -> 액티비티)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener{ documentSnapshot, firesbaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                if(documentSnapshot.data?.get("isReceivedRecycle")!=null){
                    //다이얼로그 호출
                    val dlg = ConfirmRecycleDialog(this)
                    dlg.start(this)
                }
            }

        // 보낸 재활용 인증 사진에 대한 승인이 된 경우 다이얼로그와 리워드 출력(다이얼로그 -> 리워드 액티비티)
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.collection("missionDetail")?.document("recycle")
            ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                if(documentSnapshot.data?.get("isApproved").toString().toBoolean()){
                    //confirmOk == true면 리워드 연결된 다이얼로그, false면 rejected 다이얼로그
                    if(documentSnapshot.data?.get("confirmOk").toString()=="true"){
                        //다이얼로그 호출
                        val dlg = ApproveToRewardDialog(this)
                        dlg.start(this)
                    }
                    if(documentSnapshot.data?.get("confirmOk").toString()=="false"){
                        //다이얼로그 호출
                        val dlg = ApproveRejectedDialog(this)
                        dlg.start()
                    }
                }
            }


        //미션 시작 날짜 및 초기화 날짜 계산
        val today: Calendar = Calendar.getInstance()
        val todayWeek:Int = today.get(Calendar.WEEK_OF_YEAR)

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener

                if(documentSnapshot.data?.get("creationTimestamp").toString()!=todayWeek.toString()){
                    //만일 오늘 날짜의 이번 년도 주차 수가 DB에 저장되어 있는 이번 년도 주차 수와 다르다면, 즉 새로운 주가 시작된 경우
                    //1) DB의 주차(creationTimestamp) 업데이트
                    //2) mission 수행 관련 DB 값 수정
                    val missionFlag = hashMapOf(
                        "creationTimeStamp" to todayWeek,
                        "missionMakingQuiz" to "false",
                        "missionRecycle" to "false",
                        "missionSenseQuiz" to "false",
                        "missionUserQuiz" to "false"
                    )
                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.collection("mission")?.document(fbAuth?.uid.toString())
                        ?.set(missionFlag, SetOptions.merge())?.addOnSuccessListener {
                            Log.d("Set WeekNumber to DB", "DocumentSnapshot successfully written!")
                            val RecycleRef = fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                                ?.collection("mission")?.document(fbAuth?.uid.toString())
                                ?.collection("missionDetail")?.document("recycle")

                            val updates = hashMapOf<String, Any>(
                                "isApproved" to FieldValue.delete(),
                                "confirmOk" to FieldValue.delete(),
                                "sendKakao" to FieldValue.delete(),
                                "whoApproved" to FieldValue.delete()
                            )
                            RecycleRef?.update(updates)
                        }
                        ?.addOnFailureListener { e -> Log.w("Set WeekNumber to DB", "Error writing document", e) }
                }
                else{
                    Log.d("compare week","success and same")
                }
            }


        // 네비게이션바에 리스너 부착
        navigation.setOnNavigationItemSelectedListener(this)

        // 홈 화면으로 초기화 -> 홈 아이콘 색상만 friendly_green
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, PagerHomeFragment()).commit()
        navigation.menu.getItem(1).isChecked = true

    }

    // res/menu/navigation_items.xml 파일에서 각 아이템의 id
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.button_mailbox -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, MailboxFragment())
                transaction.commit()
                return true
            }
            R.id.button_home -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, PagerHomeFragment())
                transaction.commit()
                return true
            }
            R.id.button_mypage -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, MypageFragment())
                transaction.commit()
                return true
            }
        }
        return false

    }
}