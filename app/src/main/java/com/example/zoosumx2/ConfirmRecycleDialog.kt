package com.example.zoosumx2

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView

class ConfirmRecycleDialog(context: Context) {
    private val dlg = Dialog(context)
    private lateinit var btnOk : Button

    fun start(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.confirm_recycle_dialog)
        dlg.setCancelable(false)

        btnOk = dlg.findViewById(R.id.confirm_ok)
        btnOk.setOnClickListener{
            dlg.dismiss()
        }
        dlg.show()
    }

}