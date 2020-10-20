package com.example.zoosumx2.model

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import com.example.zoosumx2.GetRewardActivity
import com.example.zoosumx2.PhotoActivity
import com.example.zoosumx2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ResidentConfirmSubDialog(context: Context) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.resident_to_reward_dialog)
        dlg.setCancelable(false)

        btnOk = dlg.findViewById(R.id.confirm_ok)
        dlg.show()

        btnOk.setOnClickListener {
            dlg.dismiss()
            val intent = Intent((context as PhotoActivity), GetRewardActivity::class.java)
            intent.putExtra("reward", 2)

            //리워드 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update(
                "rewardPoint",
                FieldValue.increment(2))
            //경험치 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("exp", FieldValue.increment(10))
            (context as PhotoActivity).startActivity(intent)
        }
    }
}