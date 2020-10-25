package com.example.zoosumx2

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zoosumx2.dialog.ConfirmOthersApproveDialog
import com.example.zoosumx2.dialog.ConfirmOthersRejectDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_confirm_others.*

class ConfirmOthersActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private var senderUID: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_others)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val storageRef = Firebase.storage.reference
        val imageView = findViewById<ImageView>(R.id.photo_square_others)

        //현재 로그인한 사용자의 isReceivedRecycle 필드에 저장되어 있는 보낸 사용자의 uid 가져오기
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                senderUID = documentSnapshot.data?.get("isReceivedRecycle").toString()

                //보낸 사용자의 uid를 통해 보낸 사용자의 photo값에 있는 사진 이름 가져오기
                fbFirestore?.collection("users")?.document(senderUID!!)
                    ?.collection("mission")?.document(senderUID!!)
                    ?.collection("missionDetail")?.document("recycle")
                    ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                        if(documentSnapshot == null) return@addSnapshotListener
                        val photoTitle: String = documentSnapshot.data?.get("photo").toString()

                        //가져온 사진을 imageView에 출력
                        val photoRef = storageRef.child("images/${photoTitle}")

                        photoRef.downloadUrl.addOnSuccessListener {result ->
                            Glide.with(this).load(result).into(imageView)
                        }.addOnFailureListener {exception ->
                            Log.d("pull trash image", "failure", exception)
                        }
                    }
            }

        fbFirestore?.collection("users")?.document(senderUID!!)
            ?.collection("mission")?.document(senderUID!!)
            ?.collection("missionDetail")?.document("recycle")
            ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                confirm_others_step1.text = documentSnapshot.data?.get("missionStep1").toString().replace("bb"," ").replace("Step1", "")
                confirm_others_step2.text = documentSnapshot.data?.get("missionStep2").toString().replace("bb"," ").replace("Step2", "")
                confirm_others_step3.text = documentSnapshot.data?.get("missionStep3").toString().replace("bb"," ").replace("Step3", "")
            }

        confirm_others_approve.setOnClickListener {
            //다이얼로그 호출
            val dlg = ConfirmOthersApproveDialog(this, senderUID!!)
            dlg.start(this)
        }

        confirm_others_reject.setOnClickListener {
            //다이얼로그 호출
            val dlg = ConfirmOthersRejectDialog(this, senderUID!!)
            dlg.start(this)
        }

    }
}