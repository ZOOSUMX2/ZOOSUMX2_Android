package com.example.zoosumx2.adapter

import android.annotation.SuppressLint
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
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = View.inflate(context, R.layout.mission_card_item, null)
        val icons: ImageView = view.findViewById(R.id.mission_card_image)
        val title: TextView = view.findViewById(R.id.mission_card_title)
        val context: TextView = view.findViewById(R.id.mission_card_context)

        val listItem: MissionItem = arrayList[position]

        icons.setImageResource(listItem.icons!!)
        title.text = listItem.title
        context.text = listItem.context
        return view
    }

}