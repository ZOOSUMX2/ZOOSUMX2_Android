package com.example.zoosumx2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

        //Todo: 가능하면 dialog 넣기
        confirm_others_approve.setOnClickListener {
            Handler().postDelayed({
                Toast.makeText(this, "승인 결과를 전송 중입니다. 시간이 소요될 수 있어요!", Toast.LENGTH_SHORT).show()
                //2) 보낸 사용자의 isApproved 필드 값 true로 변환, 보낸 사용자의 whoApproved 필드에 인증해준 사용자의 uid 저장
                val approvedInfo = hashMapOf(
                    "isApproved" to true,
                    "whoApproved" to fbAuth?.uid.toString()
                )
                fbFirestore?.collection("users")?.document(senderUID!!)
                    ?.collection("mission")?.document(senderUID!!)
                    ?.collection("missionDetail")?.document("recycle")
                    ?.set(approvedInfo, SetOptions.merge())

                //1) 현재 로그인한 사용자의 isReceivedRecycle 필드 값 null로 바꿈
                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                    ?.collection("mission")?.document(fbAuth?.uid.toString())
                    ?.update("isReceivedRecycle", null)?.addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
            }, 15000)


        }

        confirm_others_reject.setOnClickListener {
            Toast.makeText(this, "본 재활용 결과를 승인하지 않았습니다. 결과를 전송하는 중입니다.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                //1) 현재 로그인한 사용자의 isReceivedRecycle 필드 값 null로 바꿈
                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                    ?.collection("mission")?.document(fbAuth?.uid.toString())
                    ?.update("isReceivedRecycle", null)?.addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

            }, 15000)
        }

    }
}