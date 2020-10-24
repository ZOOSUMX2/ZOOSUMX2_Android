package com.example.zoosumx2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ConfirmOthersActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    private var trashBMPurl: String ?= null
    private var senderUID: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_others)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        val storageReference = Firebase.storage.reference
        val imageView = findViewById<ImageView>(R.id.photo_square_others)

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                senderUID = documentSnapshot.data?.get("isReceivedRecycle").toString()

                fbFirestore?.collection("users")?.document(senderUID!!)
                    ?.collection("mission")?.document(senderUID!!)
                    ?.collection("missionDetail")?.document("recycle")
                    ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                        if(documentSnapshot == null) return@addSnapshotListener

                        Glide.with(this).load(documentSnapshot.data?.get("photo").toString()).into(imageView)
                    }
            }
    }
}