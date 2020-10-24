package com.example.zoosumx2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FriendConfirmSubDialog(context: Context) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.friend_to_reward_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.confirm_ok)
        dlg.show()

        btnOk.setOnClickListener {
            dlg.dismiss()

            //firestore의 mission 수행 여부 true로 변경
            val missionFlag = hashMapOf(
                "missionRecycle" to "true"
            )
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.set(missionFlag, SetOptions.merge())?.addOnSuccessListener { Log.d("Set WeekNumber to DB", "DocumentSnapshot successfully written!") }
                ?.addOnFailureListener { e -> Log.w("Set WeekNumber to DB", "Error writing document", e) }

            val intent = Intent((context as PhotoActivity), GetRewardActivity::class.java)
            intent.putExtra("reward", 2)

            //리워드 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.update(
                "rewardPoint",
                FieldValue.increment(2))
            //경험치 증가
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("exp", FieldValue.increment(10))
            context.startActivity(intent)
        }
    }
}