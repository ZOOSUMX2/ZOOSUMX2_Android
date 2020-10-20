package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zoosumx2.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    fun newInstance(): HomeFragment {
        val args = Bundle()

        val frag = HomeFragment()
        frag.arguments = args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // firestore에서 데이터 가져온 후 textview에 띄우기
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                textview_username_home?.text = documentSnapshot.data?.get("nickname").toString()
                textview_island_name_home?.text =
                    documentSnapshot.data?.get("islandName").toString()
                textview_mypoint_home?.text = documentSnapshot.data?.get("rewardPoint").toString()
                textview_ranking_home?.text = documentSnapshot.data?.get("rank").toString()
            }

        // 포인트 클릭 이벤트
        linearlayout_mypoint_home?.setOnClickListener {
            val intent = Intent(context, PointActivity::class.java)
            startActivity(intent)
        }
    }
}
