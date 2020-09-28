package com.example.zoosumx2.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.zoosumx2.R
import com.example.zoosumx2.model.MissionItem

class MissionAdapter(var context: Context, var arrayList: ArrayList<MissionItem>):BaseAdapter() {

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view:View = View.inflate(context, R.layout.mission_card_item, null)
        var icons:ImageView = view.findViewById(R.id.mission_card_image)
        var title:TextView = view.findViewById(R.id.mission_card_title)
        var context:TextView = view.findViewById(R.id.mission_card_context)

        var listItem:MissionItem = arrayList.get(position)

        icons.setImageResource(listItem.icons!!)
        title.text = listItem.title
        context.text = listItem.context

        return view
    }

}