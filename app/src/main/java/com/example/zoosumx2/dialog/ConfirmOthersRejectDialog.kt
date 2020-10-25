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


class ConfirmOthersRejectDialog(context: Context, senderUID: String) {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button
    private val senderUID = senderUID
    private var nextFlag: Boolean = false

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.confirm_others_reject_dialog)
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

        ReceivedRef?.update(updates)?.addOnCompleteListener {
            btnOk.setBackgroundResource(R.drawable.rounded_rectangle_green)
            nextFlag = true
        }

        btnOk.setOnClickListener {
            if(nextFlag){
                val intent = Intent((context as ConfirmOthersActivity), MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}