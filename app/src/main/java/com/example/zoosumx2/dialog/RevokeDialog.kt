package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.zoosumx2.*
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
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 취소하기
        btnCancel = dlg.findViewById(R.id.button_cancel_revoke_dialog)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

//        // 탈퇴하기
//        btnOk = dlg.findViewById(R.id.button_ok_revoke_dialog)
//        btnOk.setOnClickListener {
//            // firestore 내 사용자 정보 삭제
//            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.delete()
//
//            // 구글 로그인 계정 삭제
//            fbAuth?.currentUser?.delete()
//                ?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        //val intent = Intent((context as SettingActivity), LoginActivity::class.java)
//                        //context.startActivity(intent)
//                    }
//                }
//
//            // 사용자의 구 주민 수 1 감소
//            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
//                ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
//                    if (documentSnapshot == null) return@addSnapshotListener
//                    fbFirestore?.collection("region")
//                        ?.document(documentSnapshot?.data?.get("addressRegion").toString())
//                        ?.update("people", FieldValue.increment(-1))
//                }
//        }
//
//        dlg.show()
    }
}