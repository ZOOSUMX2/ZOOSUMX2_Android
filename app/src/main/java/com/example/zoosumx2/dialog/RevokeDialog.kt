package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import com.example.zoosumx2.GetRewardActivity
import com.example.zoosumx2.PhotoActivity
import com.example.zoosumx2.R
import com.example.zoosumx2.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RevokeDialog(context: Context) {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button

    fun start(context: Context) {

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀바 제거
        dlg.setContentView(R.layout.revoke_dialog) // 다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false) // 다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록

        btnCancel = dlg.findViewById(R.id.button_cancel_revoke_dialog)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        btnOk = dlg.findViewById(R.id.button_ok_revoke_dialog)
        btnOk.setOnClickListener {
            // firestore 내 사용자 정보 삭제
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.delete()

            // 구글 로그인 계정 삭제
            fbAuth?.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Toast.makeText(this, "정상적으로 탈퇴되었습니다", Toast.LENGTH_LONG).show()
                        val intent =
                            Intent((context as PhotoActivity), GetRewardActivity::class.java)
                        (context as SettingActivity).startActivity(intent)
                    }
                }
        }

        dlg.show()

        btnOk.setOnClickListener {
            dlg.dismiss()
            val intent = Intent((context as PhotoActivity), GetRewardActivity::class.java)
            intent.putExtra("reward", 2)

            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update(
                "rewardPoint",
                FieldValue.increment(2)
            )
            (context as PhotoActivity).startActivity(intent)
        }
    }
}