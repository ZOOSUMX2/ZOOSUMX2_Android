package com.example.zoosumx2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button

class MissionRejectDialog(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button

    fun start(context: Context){

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.mission_reject_dialog)
        dlg.setCancelable(false)

        btnOk = dlg.findViewById(R.id.ok_btn)

        btnOk.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }
}