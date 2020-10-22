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
import com.example.zoosumx2.adapter.MissionAdapter
import com.example.zoosumx2.model.MissionItem

class MissionFragment : Fragment() , AdapterView.OnItemClickListener{

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
                val intent = Intent(context, ConfirmRecycleActivity::class.java)
                startActivity(intent)
            }
            "주민 출제 퀴즈" -> {
                val intent = Intent(context, ResidentQuizActivity::class.java)
                startActivity(intent)
            }
            "퀴즈 출제하기" -> {
                val intent = Intent(context, MakequizActivity::class.java)
                startActivity(intent)
            }
            "상식 퀴즈" -> {
                val intent = Intent(context, RandomQuizActivity::class.java)
                startActivity(intent)
            }
        }
        //Toast.makeText(activity!!.applicationContext, items.title, Toast.LENGTH_LONG).show()
    }

}