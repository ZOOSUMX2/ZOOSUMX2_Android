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
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
            ?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                val missionMakingQuizFlag = documentSnapshot.data?.get("missionMakingQuiz").toString()
                val missionRecycleFlag = documentSnapshot.data?.get("missionRecycle").toString()
                val missionSenseQuizFlag = documentSnapshot.data?.get("missionSenseQuiz").toString()
                val missionUserQuizFlag = documentSnapshot.data?.get("missionUserQuiz").toString()

                fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                    ?.collection("mission")?.document(fbAuth?.uid.toString())
                    ?.collection("missionDetail")?.document("recycle")
                    ?.addSnapshotListener{ documentSnapshot2, firebaseFirestoreException2 ->
                        if(documentSnapshot2 == null) return@addSnapshotListener
                        val approvedWaitFlag = documentSnapshot2.data?.get("isApproved").toString()
                        val sendKakaoFlag = documentSnapshot2.data?.get("sendKakao").toString()
                        val confirmOkFlag = documentSnapshot2.data?.get("comfirmOk").toString()

                        //재활용 인증 미션을 하지 않았으면 default 초록색으로
                        //재활용 인증 미션 완료 -> isApproved false: 다른 주민이 아직 승인하지 않음 -> 대기 중
                        //재활용 인증 미션 완료 -> isApproved false: 다른 주민이 부족하다고 판단 -> confirmOk False: 다이얼로그 출력 -> 완료
                        //재활용 인증 미션 완료 -> isApproved false: 카카오톡으로 보냄 -> sendKakao true: 완료
                        //총 다뤄야 할 기준 변수
                        // 1) missionRecycleFlag: 미션을 완료하였는지,
                        // 2) approvedWaitFlag: isApproved 변수가 true/false?
                        // 3) sendKakaoFlag: 카카오톡으로 보내서 미션 완료 + isApproved false인 경우 대기중을 띄우면 안되니까..
                        // 4) confrimOk: 다른 주민이 부족하다고 판단하면 false, 충분하다고 판단하면 true

                        /*if(approvedWaitFlag == "true" && confirmOkFlag == "false"){
                                //아쉽다는 다이얼로그 출력 후 //합치자 아래거랑
                                confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                                confirm_recycle_banner.text = "완료"
                            }
                            if(approvedWaitFlag == "true" && confirmOkFlag == "true"){
                                //리워드 연결된 다이얼로그 출력 후
                                confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                                confirm_recycle_banner.text = "완료"
                            }*/

                        if(missionRecycleFlag == "true" && sendKakaoFlag != "true"){
                            if(approvedWaitFlag != "true"){
                                confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_wait)
                                confirm_recycle_banner.text = "대기중"
                            }
                            confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                            confirm_recycle_banner.text = "완료"
                        }else if(missionRecycleFlag == "true" && sendKakaoFlag == "true"){
                            confirm_recycle_banner.setBackgroundResource(R.drawable.mission_card_banner_complete)
                            confirm_recycle_banner.text = "완료"
                        }else{user_quiz_banner.setBackgroundResource(R.drawable.mission_card_banner_challenge)
                            confirm_recycle_banner.text = "도전"}

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

}