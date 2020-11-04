package com.zoosumzoosum.zoosumx2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_confirm_recycle.*

class ConfirmRecycleActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    // 백 버튼 금지
    override fun onBackPressed() {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_recycle)

        Toast.makeText(this, "재활용 순서에 알맞게 체크해주세요.", Toast.LENGTH_LONG).show()

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        fbFirestore?.collection("RecycleSteps")?.whereEqualTo("new", true)
            ?.addSnapshotListener{documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener
                for (snapshot in documentSnapshot) {
                    confirm_title.text = snapshot.getString("missionTitle").toString().replace("bb", "\n")
                    text_step1.text = snapshot.getString("missionStep1").toString().replace("bb", "\n")
                    text_step2.text = snapshot.getString("missionStep2").toString().replace("bb", "\n")
                    text_step3.text = snapshot.getString("missionStep3").toString().replace("bb", "\n")

                    //DB에서 받아온 Step들 다시 users 아래의 필드에 저장하기
                    val RecycleStepInfo = hashMapOf(
                        "missionTitle" to snapshot.getString("missionTitle").toString().replace("\n", "bb"),
                        "missionStep1" to snapshot.getString("missionStep1").toString().replace("\n", "bb"),
                        "missionStep2" to snapshot.getString("missionStep2").toString().replace("\n", "bb"),
                        "missionStep3" to snapshot.getString("missionStep3").toString().replace("\n", "bb")
                    )

                    fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                        ?.collection("mission")?.document(fbAuth?.uid.toString())
                        ?.collection("missionDetail")?.document("recycle")?.set(RecycleStepInfo, SetOptions.merge())
                }
            }

        // status bar 색상 변경
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendly_green)

        val backButton = findViewById<ImageButton>(R.id.confirm_recycle_back)
        backButton.setOnClickListener {
            finish()
        }

        val checkboxS1 = findViewById<CheckBox>(R.id.checkbox_step1)
        val textS1 = findViewById<TextView>(R.id.text_step1)
        val checkboxS2 = findViewById<CheckBox>(R.id.checkbox_step2)
        val textS2 = findViewById<TextView>(R.id.text_step2)
        val checkboxS3 = findViewById<CheckBox>(R.id.checkbox_step3)
        val textS3 = findViewById<TextView>(R.id.text_step3)
        val nextButton = findViewById<Button>(R.id.confirm_recycle_next)

        //초기 상태
        checkboxS1.isChecked = false
        textS1.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkboxS2.isChecked = false
        textS2.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        checkboxS3.isChecked = false
        textS3.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
        nextButton.isSelected = false
        nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))

        //step1
        checkboxS1.setOnClickListener {
            if (!checkboxS2.isChecked && !checkboxS3.isChecked) {
                if(checkboxS1.isChecked){
                    textS1.setTextColor(ContextCompat.getColor(this, R.color.colorText))
                } else {textS1.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))}
            }
            if (checkboxS2.isChecked || checkboxS3.isChecked) {
                checkboxS1.isChecked = true
            }
        }

        //step2
        checkboxS2.setOnClickListener {
            if (!checkboxS1.isChecked) {
                checkboxS2.isChecked = false
            }
            else if(checkboxS3.isChecked){
                checkboxS2.isChecked = true
            }
            if(checkboxS2.isChecked){
                textS2.setTextColor(ContextCompat.getColor(this, R.color.colorText))
            } else {textS2.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))}
        }

        //step3
        checkboxS3.setOnClickListener {
            if (checkboxS1.isChecked && checkboxS2.isChecked) {
                if (checkboxS1.isChecked && checkboxS2.isChecked && checkboxS3.isChecked) {
                    textS3.setTextColor(ContextCompat.getColor(this, R.color.colorText))
                    nextButton.isSelected = true
                    nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorText))
                } else {
                    textS3.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
                    nextButton.isSelected = false
                    nextButton.setTextColor(ContextCompat.getColor(this, R.color.colorSoftGray))
                }
            } else {
                checkboxS3.isChecked = false
            }


        }


        nextButton.setOnClickListener {
            if (checkboxS1.isChecked && checkboxS2.isChecked && checkboxS3.isChecked) {
                val intent = Intent(this, PhotoActivity::class.java)
                intent.putExtra("missionTitle",confirm_title.text.toString())
                intent.putExtra("missionContent",confirm_ment_sub.text.toString())
                startActivity(intent)
            } else{
                nextButton.isSelected = false
            }
        }

    }
}