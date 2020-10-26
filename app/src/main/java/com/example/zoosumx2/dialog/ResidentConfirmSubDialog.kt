package com.example.zoosumx2.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.zoosumx2.GetRewardActivity
import com.example.zoosumx2.MainActivity
import com.example.zoosumx2.PhotoActivity
import com.example.zoosumx2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.ArrayList
import kotlin.random.Random

class ResidentConfirmSubDialog(context: Context, curPhotoPath: String, timestamp: String?) {
    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private val dlg = Dialog(context)
    private lateinit var btnOk: Button
    private lateinit var title: TextView
    private val limitInt: Long = 1
    private var nextFlag: Boolean = false

    val curPhotoPath = curPhotoPath
    val timestamp = timestamp

    fun start(context: Context){

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()
        val storage = Firebase.storage
        val storageRef = storage.reference

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.resident_to_reward_dialog)
        dlg.setCancelable(false)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnOk = dlg.findViewById(R.id.resident_to_reward_goHome)
        title = dlg.findViewById(R.id.resident_to_reward_title)
        dlg.show()


        //파이어베이스 스토리지에 업로드
        val recyclesRef = storageRef.child("turtle.png")
        val recycleImagesRef = recyclesRef.child("images/turtle.png")
        recyclesRef.name == recycleImagesRef.name
        recyclesRef.path == recycleImagesRef.path

        val file = Uri.fromFile(File(curPhotoPath))
        val trashRef = storageRef.child("images/${file.lastPathSegment}")
        val uploadTask = trashRef.putFile(file)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            //재활용 사진 및 미션 정보 DB에 업로드
            val recyclePhotoInfo = hashMapOf(
                "isApproved" to false,
                "sentTimestamp" to timestamp,
                "photo" to "${file.lastPathSegment}"
            )
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.collection("missionDetail")?.document("recycle")?.set(recyclePhotoInfo, SetOptions.merge())

            //재활용 사진 및 정보 <받는 사람의> DB에 업로드
            val randomFlag: ArrayList<String> = ArrayList() //랜덤으로 뽑아올 필드 값 문자열 세팅
            randomFlag.add("uid")
            randomFlag.add("addressRegion")
            randomFlag.add("exp")

            val randomInsertArray: ArrayList<String> = ArrayList() //실제 firestore 접근을 위해 넣을 필드 값

            for(i in 1..3){
                var tempInt: Int = Random.nextInt(randomFlag.size)
                randomInsertArray.add(randomFlag[tempInt])
                randomFlag.removeAt(tempInt)
                if(randomFlag.isEmpty())
                    break
            }

            val randomDescendingFlag: Int = Random.nextInt(8)
            val usersRef = fbFirestore?.collection("users")

            when(randomDescendingFlag){
                0 -> { usersRef?.orderBy(randomInsertArray[0])?.orderBy(randomInsertArray[1])?.orderBy(randomInsertArray[2])?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                1 -> { usersRef?.orderBy(randomInsertArray[0], Query.Direction.DESCENDING)?.orderBy(randomInsertArray[1])?.orderBy(randomInsertArray[2])?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                2 -> { usersRef?.orderBy(randomInsertArray[0])?.orderBy(randomInsertArray[1], Query.Direction.DESCENDING)?.orderBy(randomInsertArray[2])?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                3 -> { usersRef?.orderBy(randomInsertArray[0])?.orderBy(randomInsertArray[1])?.orderBy(randomInsertArray[2], Query.Direction.DESCENDING)?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                4 -> { usersRef?.orderBy(randomInsertArray[0], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[1], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[2])?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                5 -> { usersRef?.orderBy(randomInsertArray[0], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[1])
                    ?.orderBy(randomInsertArray[2], Query.Direction.DESCENDING)?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                6 -> { usersRef?.orderBy(randomInsertArray[0])
                    ?.orderBy(randomInsertArray[1], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[2], Query.Direction.DESCENDING)?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
                7 -> { usersRef?.orderBy(randomInsertArray[0], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[1], Query.Direction.DESCENDING)
                    ?.orderBy(randomInsertArray[2], Query.Direction.DESCENDING)?.limit(limitInt)
                    ?.get()?.addOnSuccessListener { result ->
                        for(document in result){
                            fbFirestore?.collection("users")?.document(document.get("uid").toString())
                                ?.collection("mission")?.document(document.get("uid").toString())?.update("isReceivedRecycle",fbAuth?.uid.toString())
                        }
                    }}
            }
        }


        btnOk.setOnClickListener {

            //firestore의 mission 수행 여부 true로 변경
            val missionFlag = hashMapOf(
                "missionRecycle" to "true"
            )
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.collection("mission")?.document(fbAuth?.uid.toString())
                ?.set(missionFlag, SetOptions.merge())?.addOnSuccessListener {
                    nextFlag = true
                    btnOk.setBackgroundResource(R.drawable.rounded_rectangle_green)
                    title.text = "재활용 인증 요청 전송 완료!"
                }
            if(nextFlag){
                dlg.dismiss()
                val intent = Intent((context as PhotoActivity), MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}