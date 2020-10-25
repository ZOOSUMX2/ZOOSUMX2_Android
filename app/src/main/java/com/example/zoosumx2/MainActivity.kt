package com.example.zoosumx2

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.dialog.ApproveToRewardDialog
import com.example.zoosumx2.dialog.ConfirmRecycleDialog
import com.example.zoosumx2.menu.MailboxFragment
import com.example.zoosumx2.menu.MypageFragment
import com.example.zoosumx2.menu.PagerHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

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
                    //isApproved 필드 false로 변경
                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.collection("mission")?.document(fbAuth?.uid.toString())
                        ?.collection("missionDetail")?.document("recycle")?.update("isApproved", false)

                    //다이얼로그 호출
                    val dlg = ApproveToRewardDialog(this)
                    dlg.start(this)
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