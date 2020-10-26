package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import androidx.core.content.contentValuesOf
import com.example.zoosumx2.ConfirmOthersActivity
import com.example.zoosumx2.GetRewardActivity
import com.example.zoosumx2.MainActivity
import com.example.zoosumx2.R

class LevelUpDialog(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context) {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.level_up_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.ok_btn)
        dlg.show()

        btnOk.setOnClickListener {
            dlg.dismiss()
            val intent = Intent((context as GetRewardActivity), MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
