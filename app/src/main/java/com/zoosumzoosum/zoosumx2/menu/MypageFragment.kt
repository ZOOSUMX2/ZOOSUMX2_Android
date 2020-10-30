package com.zoosumzoosum.zoosumx2.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zoosumzoosum.zoosumx2.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_mypage.*

class MypageFragment : Fragment() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                textview_username_mypage?.text =
                    documentSnapshot.data?.get("nickname").toString() // 이름
                textview_island_name_mypage?.text =
                    documentSnapshot.data?.get("islandName").toString() // 섬 이름
                textview_mylevel_mypage?.text =
                    documentSnapshot.data?.get("level").toString() // 현재 레벨
                // 다음 레벨
                if (documentSnapshot.data?.get("level").toString() == "5") {
                    textview_next_level_mypage?.text = "Max"
                } else {
                    textview_next_level_mypage?.text =
                        (documentSnapshot.data?.get("level").toString().toIntOrNull()
                            ?.plus(1)).toString()
                }
                when (documentSnapshot.data?.get("level").toString().toIntOrNull()) {
                    1 -> textview_comment_mypage?.text = "섬이 드디어 새로운 주인을 찾았네요!"
                    2 -> textview_comment_mypage?.text = "섬의 자연이 점점 되돌아오고 있나봐요"
                    3 -> textview_comment_mypage?.text = "섬에 동물들이 놀러오는 소리가 들려요:)"
                    4 -> textview_comment_mypage?.text = "넓고 아름다운 섬을 가꾸고 있는 요즘"
                    else -> textview_comment_mypage?.text = "세상에서 제일 깨끗하고 아름다운 섬"
                }
            }

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                var exp = documentSnapshot.data?.get("exp").toString().toIntOrNull()
                val level = documentSnapshot.data?.get("level").toString().toIntOrNull()

                val min = when (level) {
                    1 -> 0
                    2 -> 100
                    3 -> 300
                    4 -> 600
                    else -> 1000
                }

                val max = when (level) {
                    1 -> 99
                    2 -> 299
                    3 -> 599
                    4 -> 999
                    else -> 1500
                }

                val sleep = when (level) {
                    1 -> 40
                    2 -> 25
                    3 -> 15
                    4 -> 10
                    else -> 5
                }

                val progressBar = this.progressbar_mypage
                if (progressBar != null) {
                    progressBar.max = max - min
                }
                if (level == 5) {
                    exp = 1500
                }

                var tmp = 0
                Thread {
                    if (exp != null) {
                        while (tmp <= (exp - min)) {
                            try {
                                tmp += 3
                                Thread.sleep((sleep).toLong())
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            // 프로그래스바 업데이트
                            progressBar?.progress = tmp
                        }
                    }
                }.start()
            }

        // 주섬주섬 상점
        linearlayout_store_mypage.setOnClickListener {
            val intent = Intent(context, StoreActivity::class.java)
            startActivity(intent)
        }

        // 우리동네 현황
        linearlayout_town_mypage.setOnClickListener {
            val intent = Intent(context, TownActivity::class.java)
            startActivity(intent)
        }

        // 환경설정
        linearlayout_setting_mypage.setOnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

    }
}