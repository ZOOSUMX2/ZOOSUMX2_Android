package com.example.zoosumx2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.zoosumx2.menu.HomeFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_confirm_recycle.*
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.AsynchronousFileChannel.open
import java.text.SimpleDateFormat
import java.util.*

class PhotoActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1 //카메라 사진 촬영 요청 코드
    lateinit var curPhotoPath: String //문자열 형태의 사진 경로 값

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        // status bar 색상 변경
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendly_green)

        setPermission() //권한을 체크하는 메소드 수행

        //뒤로 가기
        val backButton = findViewById<ImageButton>(R.id.photo_back)
        backButton.setOnClickListener {
            finish()
        }

        //카메라 연동
        val open_camera = findViewById<Button>(R.id.btn_open_camera)
        open_camera.setOnClickListener{
            takeCapture() //기본 카메라 앱 실행
        }


        //지역 주민에게 인증 받기, dialog 호출
        val sendButton = findViewById<Button>(R.id.confirm_to_resident)
        sendButton.setOnClickListener{
            val dlg = ConfirmRecycleDialog(this)
            dlg.start()
        }
    }

    //사진 촬영
    private fun takeCapture() {
        //기본 카메라 앱 실행
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{takePictureIntent->
            takePictureIntent.resolveActivity(packageManager)?.also{
                val photoFile: File? = try{
                    createImageFile()
                } catch (ex: IOException){
                    null
                }
                photoFile?.also{
                    val photoURI: Uri =FileProvider.getUriForFile(
                        this,
                        "com.example.zoosumx2.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /*이미지 파일 생성*/
    private fun createImageFile(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd.HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_",".jpg",storageDir)
            .apply{curPhotoPath=absolutePath}
    }

    /*테드 퍼미션 설정*/
    private fun setPermission() {
        val permission = object: PermissionListener{
            override fun onPermissionGranted() { //설정해 놓은 위험권한이 허용될 경우
                Toast.makeText(this@PhotoActivity, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { //설정해 놓은 위험권한 들 중 거부를 한 경우
                Toast.makeText(this@PhotoActivity, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
            .check()
    }

    // startActivityForResult를 통해 기본 카메라 앱으로부터 받아온 사진 결과 값
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //이미지를 성공적으로 가져왔을 경우
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap: Bitmap
            val file = File(curPhotoPath)
            if(Build.VERSION.SDK_INT<28) { //안드로이드 9.0 Pie 버전 보다 낮을 경우
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
                square_photo.setImageBitmap(bitmap)
            }
            else{ //안드로이드 9.0 Pie 버전 보다 높을 경우
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                bitmap = ImageDecoder.decodeBitmap(decode)
                square_photo.setImageBitmap(bitmap)
            }
            savePhoto(bitmap)
        }
    }

    //갤러리에 저장
    private fun savePhoto(bitmap: Bitmap) {
        val folderPath =
            Environment.getExternalStorageDirectory().absolutePath + "/Pictures/" //사진폴더로 저장하기 위한 경로
        val timestamp: String = SimpleDateFormat("yyyyMMdd.HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if (!folder.isDirectory) {//현재 해당 경로에 폴더가 존재하는지 확인, 존재하지 않을 경우
            folder.mkdirs() //make directory
        }

        //실제 저장처리
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        Toast.makeText(this, " 사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}