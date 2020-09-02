package com.example.zoosumx2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PointAdapter(val context: Context, val pointList: ArrayList<PointDetailsData>) :
    RecyclerView.Adapter<PointAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.point_details_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return pointList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(pointList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pointNote = itemView.findViewById<TextView>(R.id.textview_note_point_details)
        val points = itemView.findViewById<TextView>(R.id.textview_point_point_details)
        val date = itemView.findViewById<TextView>(R.id.textview_date_point_details)
        val time = itemView.findViewById<TextView>(R.id.textview_time_point_details)

        fun bind(pointDetailsData: PointDetailsData) {

            // TextView와 String, Int 데이터를 연결
            pointNote?.text = pointDetailsData.noteData
            points?.text = pointDetailsData.pointsData.toString()
            date?.text = pointDetailsData.dateData
            time?.text = pointDetailsData.timeData
        }
    }
}