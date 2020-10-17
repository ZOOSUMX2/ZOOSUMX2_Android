package com.example.zoosumx2

import android.Manifest.permission.CAMERA
import android.Manifest.permission_group.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_confirm_others.*
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class PhotoActivity : AppCompatActivity() {

    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99
    val FLAG_REQ_CAMERA = 101

    var send_permission = false //사진이 등록되지 않을 경우 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        // status bar 색상 변경
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendly_green)


        //뒤로 가기
        val backButton = findViewById<ImageButton>(R.id.photo_back)
        backButton.setOnClickListener {
            finish()
        }

        //카메라 연동
        val open_camera = findViewById<TextView>(R.id.btn_open_camera)
        square_photo.setOnClickListener{
            if(isPermitted(CAMERA_PERMISSION)){
                openCamera() //기본 카메라 앱 실행
            } else{
                ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, FLAG_PERM_CAMERA)
            }
        }


        //지역 주민에게 인증 받기, dialog 호출
        val sendButton = findViewById<Button>(R.id.confirm_to_resident)
        sendButton.setOnClickListener{
            if(send_permission){
                val dlg = ConfirmRecycleDialog(this)
                dlg.start(this)
            }
            else{
                Toast.makeText(this, "사진을 등록해주세요.", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun isPermitted(permissions:Array<String>): Boolean{
        for(permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_CAMERA ->{
                    val bitmap = data?.extras?.get("data") as Bitmap
                    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 956, 846, false)

                    camera_icon.visibility = View.INVISIBLE
                    btn_open_camera.visibility = View.INVISIBLE
                    square_photo.setImageBitmap(scaledBitmap)

                    confirm_to_friend.isSelected = true
                    confirm_to_friend.setTextColor(ContextCompat.getColor(this, R.color.friendly_green))
                    confirm_to_resident.isSelected = true
                    confirm_to_resident.setTextColor(ContextCompat.getColor(this, R.color.friendly_green))

                    send_permission = true
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            FLAG_PERM_CAMERA ->{
                var checked = true
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        checked = false
                        break
                    }
                }
                if(checked){
                    openCamera()
                }
            }
        }
    }





}