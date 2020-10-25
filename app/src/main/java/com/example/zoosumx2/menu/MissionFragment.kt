package com.example.zoosumx2.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import com.example.zoosumx2.*
import com.example.zoosumx2.dialog.MissionRejectDialog
import com.example.zoosumx2.adapter.MissionAdapter
import com.example.zoosumx2.model.MissionItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class MissionFragment : Fragment() , AdapterView.OnItemClickListener{

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    var missionMakingQuizFlag: String? = null
    var missionRecycleFlag: String? = null
    var missionSenseQuizFlag: String? = null
    var missionUserQuizFlag: String? = null


    fun newInstance():MissionFragment{
        val args=Bundle()

        val frag=MissionFragment()
        frag.arguments=args

        return frag
    }

    private var arrayList:ArrayList<MissionItem> ?= null
    private var gridView: GridView?= null
    private var missionAdapter: MissionAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mission, container, false)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        // firestore에서 데이터 가져와서 미션 수행가능여부 결정하기
        fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())?.collection("mission")?.document(fbAuth?.uid.toString())
            ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot == null) return@addSnapshotListener
                missionMakingQuizFlag = documentSnapshot.data?.get("missionMakingQuiz").toString()
                missionRecycleFlag = documentSnapshot.data?.get("missionRecycle").toString()
                missionSenseQuizFlag = documentSnapshot.data?.get("missionSenseQuiz").toString()
                missionUserQuizFlag = documentSnapshot.data?.get("missionUserQuiz").toString()
            }

        gridView = view.findViewById(R.id.gridview_mission_card)
        arrayList = ArrayList()
        arrayList = setDataList()
        missionAdapter = MissionAdapter(activity!!.applicationContext, arrayList!!)
        gridView?.adapter = missionAdapter
        gridView?.onItemClickListener = this

        return view
    }



    private fun setDataList() : ArrayList<MissionItem>{

        val arrayList: ArrayList<MissionItem> = ArrayList()

        arrayList.add(MissionItem(R.drawable.icon_confirmrecycle, "재활용 인증", "재활용 쓰레기를 올바르게\n처리하고, 주변 사람들에게\n인증하세요"))
        arrayList.add(MissionItem(R.drawable.icon_residentquiz, "주민 출제 퀴즈", "동네 주민들은 어떤\n문제를 냈을까? 궁금하면\n지금 바로 도전!"))
        arrayList.add(MissionItem(R.drawable.icon_makequiz, "퀴즈 출제하기", "분리배출에 대한 이야기를\n읽고, 동네 주민들에게\n직접 문제를 내 볼까요?"))
        arrayList.add(MissionItem(R.drawable.icon_randomquiz, "상식 퀴즈", "분리배출, 얼마나 알고\n계신가요? 퀴즈로 상식을\n확인해 보세요!"))

        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val items: MissionItem = arrayList!!.get(position)
        when(items.title){
            "재활용 인증" -> {
                if (missionRecycleFlag == "false") {
                    val intent = Intent(context, ConfirmRecycleActivity::class.java)
                    startActivity(intent)
                } else {
                    //다이얼로그 호출
                    val dlg = MissionRejectDialog(requireContext())
                    dlg.start()
                }
            }
            "주민 출제 퀴즈" -> {
                if (missionUserQuizFlag == "false") {
                    val intent = Intent(context, ResidentQuizActivity::class.java)
                    startActivity(intent)
                } else {
                    //다이얼로그 호출
                    val dlg = MissionRejectDialog(requireContext())
                    dlg.start()
                }

            }
            "퀴즈 출제하기" -> {
                if (missionMakingQuizFlag == "false") {
                    val intent = Intent(context, MakequizActivity::class.java)
                    startActivity(intent)
                } else {
                    //다이얼로그 호출
                    val dlg = MissionRejectDialog(requireContext())
                    dlg.start()
                }
            }
            "상식 퀴즈" -> {
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
        //Toast.makeText(activity!!.applicationContext, items.title, Toast.LENGTH_LONG).show()
    }

}