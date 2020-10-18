package com.example.zoosumx2

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.zoosumx2.menu.MailboxFragment
import com.example.zoosumx2.menu.MypageFragment
import com.example.zoosumx2.menu.PagerHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 네비게이션바에 리스너 부착
        navigation.setOnNavigationItemSelectedListener(this)

        // 홈 화면으로 초기화 -> 홈 아이콘 색상만 friendly_green
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, PagerHomeFragment()).commit()
        navigation.menu.getItem(1).isChecked = true

    }

    // res/menu/navigation_items.xml 파일에서 각 아이템의 id
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.button_mailbox -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, MailboxFragment())
                transaction.commit()
                return true
            }
            R.id.button_home -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, PagerHomeFragment())
                transaction.commit()
                return true
            }
            R.id.button_mypage -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.fade_in4, R.anim.fade_out4)
                transaction.replace(R.id.frame_main, MypageFragment())
                transaction.commit()
                return true
            }
        }
        return false

    }
}