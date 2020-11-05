package com.zoosumzoosum.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.zoosumzoosum.zoosumx2.ConfirmOthersActivity
import com.zoosumzoosum.zoosumx2.MainActivity
import com.zoosumzoosum.zoosumx2.R
import com.zoosumzoosum.zoosumx2.UserNameActivity
import kotlinx.android.synthetic.main.activity_user_name.*

class WriteUserInfoDialog(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context) {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.write_user_info_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.userInfo_goback)
        dlg.show()

        btnOk.setOnClickListener {
            dlg.dismiss()
            val intent = Intent(context, UserNameActivity::class.java)
            context.startActivity(intent)
        }

    }
}