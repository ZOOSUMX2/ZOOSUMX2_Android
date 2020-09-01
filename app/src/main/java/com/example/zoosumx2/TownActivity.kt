package com.example.zoosumx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_town.*

class TownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_town)

        // Todo : 현재는 하드코딩, 이후에 데이터 받아와서 처리하는것으로 수정
        val rankingList = arrayListOf<RankingData>(
            RankingData(1, "penguin", "권*엽", 10, 6830),
            RankingData(2, "penguin", "김*이", 10, 6210),
            RankingData(3, "penguin", "조*비", 10, 5840),
            RankingData(4, "penguin", "김*림", 9, 3250),
            RankingData(5, "penguin", "김*영", 9, 3190),
            RankingData(6, "penguin", "한*수", 9, 3010),
            RankingData(7, "penguin", "강*원", 9, 2980),
            RankingData(8, "penguin", "김*진", 9, 2790),
            RankingData(9, "penguin", "박*훈", 8, 1500),
            RankingData(10, "penguin", "고*균", 8, 1430)
        )

        val mainAdapter = MainAdapter(this, rankingList)
        recyclerview_mypage_ranking.adapter = mainAdapter

        val lm = LinearLayoutManager(this)
        recyclerview_mypage_ranking.layoutManager = lm
        recyclerview_mypage_ranking.setHasFixedSize(true)

        val button = findViewById<ImageButton>(R.id.imagebutton_back_town)
        button.setOnClickListener {
            // back button 클릭 시 현재 activity 종료하고 이전 화면(마이페이지)으로 돌아감
            finish()
        }
    }
}