package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_town.*
import kotlinx.android.synthetic.main.ranking_list_item.view.*

class TownActivity : AppCompatActivity() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_town)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        recyclerview_mypage_ranking.adapter = TownRankingAdapter()
        recyclerview_mypage_ranking.layoutManager = LinearLayoutManager(this)
        recyclerview_mypage_ranking.setHasFixedSize(true) // recyclerview 크기 고정

        //val myranking = findViewById<TextView>(R.id.textview_myranking_town)
        val username = findViewById<TextView>(R.id.textview_username_town)
        val myexp = findViewById<TextView>(R.id.textview_myexp_town)
        val mylevel = findViewById<TextView>(R.id.textview_mylevel_town)

        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                username.text = documentSnapshot.data?.get("nickname").toString()
                myexp.text = documentSnapshot.data?.get("exp").toString()
                mylevel.text = documentSnapshot.data?.get("level").toString()
            }

        val button = findViewById<ImageButton>(R.id.imagebutton_back_town)
        button.setOnClickListener {
            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }
    }

    inner class TownRankingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // RankingData 클래서 ArrayList 생성
        val townRanking: ArrayList<RankingData> = arrayListOf()

        // townRanking의 문서를 불러온 뒤 RankingData로 변환해서 ArrayList에 담음
        init {
            // 용산구 주민 중 1~100등의 정보 가져옴
            fbFirestore?.collection("users")?.whereEqualTo("addressRegion", "용산구")
                ?.orderBy("exp", Query.Direction.DESCENDING)?.limit(100)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    // ArrayList 비워줌
                    townRanking.clear()

                    if (querySnapshot != null) {
                        for (snapshot in querySnapshot.documents) {
                            val item = snapshot.toObject(RankingData::class.java)
                            if (item != null) {
                                townRanking.add(item)
                            }
                        }
                    }
                    notifyDataSetChanged()
                }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.ranking_list_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as ViewHolder).itemView

            viewHolder.textview_name_town.text = townRanking[position].nickname
            viewHolder.textview_level_town.text = townRanking[position].level.toString()
            viewHolder.textview_exp_town.text = townRanking[position].exp.toString()
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return townRanking.size
        }

    }
}