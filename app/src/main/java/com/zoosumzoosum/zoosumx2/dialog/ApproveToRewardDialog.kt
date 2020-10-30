package com.zoosumzoosum.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.zoosumzoosum.zoosumx2.GetRewardActivity
import com.zoosumzoosum.zoosumx2.MainActivity
import com.zoosumzoosum.zoosumx2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class ApproveToRewardDialog(context: Context) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.recycle_approve_reward_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.approve_to_reward_ok)
        dlg.show()

        btnOk.setOnClickListener {
            val recycleRef = fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.collection("missionDetail")?.document("recycle")

            val updates = hashMapOf<String, Any>(
                "confirmOk" to FieldValue.delete()
            )
            recycleRef?.update(updates)

            val intent = Intent((context as MainActivity), GetRewardActivity::class.java)
            intent.putExtra("reward", 2)

            dlg.dismiss()

            //리워드 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update(
                "rewardPoint",
                FieldValue.increment(2)
            )
            context.startActivity(intent)
        }
    }
}