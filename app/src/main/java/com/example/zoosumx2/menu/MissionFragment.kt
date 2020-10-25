package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zoosumx2.*
import com.example.zoosumx2.dialog.MissionRejectDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_mission.*

class MissionFragment : Fragment(){

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null


    fun newInstance():MissionFragment{
        val args=Bundle()

        val frag=MissionFragment()
        frag.arguments=args

        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_mission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // firestore에서 데이터 가져와서 미션 수행가능여부 결정하기
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                val missionMakingQuizFlag = documentSnapshot.data?.get("missionMakingQuiz").toString()
                val missionRecycleFlag = documentSnapshot.data?.get("missionRecycle").toString()
                val missionSenseQuizFlag = documentSnapshot.data?.get("missionSenseQuiz").toString()
                val missionUserQuizFlag = documentSnapshot.data?.get("missionUserQuiz").toString()

                if(missionRecycleFlag == "true"){
                    confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                    confirm_recycle_banner.text = "완료"
                }

                if(missionUserQuizFlag == "true"){
                    user_quiz_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                    user_quiz_banner.text = "완료"
                }

                if(missionMakingQuizFlag == "true"){
                    making_quiz_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                    making_quiz_banner.text = "완료"
                }

                if(missionSenseQuizFlag == "true"){
                    random_quiz_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                    random_quiz_banner.text = "완료"
                }

                mission_confirm_recycle.setOnClickListener{
                    if (missionRecycleFlag == "false") {
                        val intent = Intent(context, ConfirmRecycleActivity::class.java)
                        startActivity(intent)
                    } else {
                        //다이얼로그 호출
                        val dlg = MissionRejectDialog(requireContext())
                        dlg.start()
                    }
                }
                mission_user_quiz.setOnClickListener{
                    if (missionUserQuizFlag == "false") {
                        val intent = Intent(context, ResidentQuizActivity::class.java)
                        startActivity(intent)
                    } else {
                        //다이얼로그 호출
                        val dlg = MissionRejectDialog(requireContext())
                        dlg.start()
                    }
                }
                mission_making_quiz.setOnClickListener{
                    if (missionMakingQuizFlag == "false") {
                        val intent = Intent(context, MakequizActivity::class.java)
                        startActivity(intent)
                    } else {
                        //다이얼로그 호출
                        val dlg = MissionRejectDialog(requireContext())
                        dlg.start()
                    }
                }
                mission_random_quiz.setOnClickListener {
                    if (missionSenseQuizFlag == "false") {
                        val intent = Intent(context, RandomQuizActivity::class.java)
                        startActivity(intent)
                    } else {
                        //다이얼로그 호출
                        val dlg = MissionRejectDialog(requireContext())
                        dlg.start()
                    }
                }
            }

    }

}