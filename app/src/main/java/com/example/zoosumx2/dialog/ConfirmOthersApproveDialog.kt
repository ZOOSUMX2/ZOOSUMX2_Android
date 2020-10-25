package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.zoosumx2.ConfirmOthersActivity
import com.example.zoosumx2.MainActivity
import com.example.zoosumx2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ConfirmOthersApproveDialog(context: Context, senderUID: String) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button
    private val senderUID = senderUID
    private var nextFlag:Boolean = false

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.confirm_others_approve_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.confirm_approve_ok)
        dlg.show()

        //1) 현재 로그인한 사용자의 isReceivedRecycle 필드 값 null로 바꿈
        val ReceivedRef = fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())

        val updates = hashMapOf<String, Any>(
            "isReceivedRecycle" to FieldValue.delete()
        )

        //2) 보낸 사용자의 isApproved 필드 값 true로 변환, 보낸 사용자의 whoApproved 필드에 인증해준 사용자의 uid 저장
        ReceivedRef?.update(updates)?.addOnCompleteListener {
            val approvedInfo = hashMapOf(
                "isApproved" to true,
                "whoApproved" to fbAuth?.uid.toString()
            )
            fbFirestore?.collection("users")?.document(senderUID!!)
                ?.collection("mission")?.document(senderUID!!)
                ?.collection("missionDetail")?.document("recycle")
                ?.set(approvedInfo, SetOptions.merge())?.addOnCompleteListener {
                    btnOk.setBackgroundResource(R.drawable.rounded_rectangle_green)
                    nextFlag = true
                }
        }

        btnOk.setOnClickListener {
            if(nextFlag){
                val intent = Intent((context as ConfirmOthersActivity), MainActivity::class.java)
                context.startActivity(intent)
            }
        }

    }
}