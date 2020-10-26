package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.zoosumx2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ApproveRejectedDialog(context: Context) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()


        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.recycle_approve_rejected_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.approve_rejected_ok)
        dlg.show()

        btnOk.setOnClickListener {
            val RecycleRef = fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.collection("missionDetail")?.document("recycle")

            val updates = hashMapOf<String, Any>(
                "confirmOk" to FieldValue.delete()
            )
            RecycleRef?.update(updates)

            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.update("mission", FieldValue.increment(1))

            dlg.dismiss()
        }

    }
}