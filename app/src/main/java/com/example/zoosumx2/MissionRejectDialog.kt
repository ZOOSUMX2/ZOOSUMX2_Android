package com.example.zoosumx2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button

class MissionRejectDialog(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start() {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.mission_reject_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.ok_btn)

        btnOk.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }
}