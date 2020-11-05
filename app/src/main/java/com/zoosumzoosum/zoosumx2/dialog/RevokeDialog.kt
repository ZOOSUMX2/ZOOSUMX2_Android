package com.zoosumzoosum.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.zoosumzoosum.zoosumx2.*

class RevokeDialog(context: Context) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button

    fun start(context: Context) {

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.revoke_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnCancel = dlg.findViewById(R.id.button_cancel_revoke_dialog)
        btnOk = dlg.findViewById(R.id.button_ok_revoke_dialog)
        dlg.show()

        // 취소하기 클릭
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        // 탈퇴하기 클릭
        btnOk.setOnClickListener {
            // 사용자가 속해 있던 구의 주민 수 1 감소
            fbFirestore?.collection("users")?.document(fbAuth!!.uid.toString())
                ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    if (documentSnapshot == null) return@addSnapshotListener
                    val region = documentSnapshot.data?.get("addressRegion").toString()
                    fbFirestore?.collection("region")?.document(region)
                        ?.update("people", FieldValue.increment(-1))
                }

            // firestore 내 사용자 정보 삭제
            fbFirestore?.collection("users")?.document(fbAuth!!.uid.toString())?.delete()

            // 구글 로그인 계정 삭제
            fbAuth!!.currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent((context as SettingActivity), LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                }
        }
    }
}