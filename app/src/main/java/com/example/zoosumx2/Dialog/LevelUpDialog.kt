package com.example.zoosumx2.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import com.example.zoosumx2.R

class LevelUpDialog(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start() {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.level_up_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.confirm_ok)
        btnOk.setOnClickListener {
            dlg.dismiss()
//            val intent = Intent((context as GetRewardActivity), MainActivity::class.java)
//            context.startActivity(intent)
        }
        dlg.show()
    }
}
