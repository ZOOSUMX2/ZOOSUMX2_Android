package com.zoosumzoosum.zoosumx2.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zoosumzoosum.zoosumx2.GetRegionActivity
import com.zoosumzoosum.zoosumx2.R
import com.zoosumzoosum.zoosumx2.model.RegionData
import kotlinx.android.synthetic.main.region_item.view.*

class RegionAdapter : RecyclerView.Adapter<Holder>() {
    var regionData = mutableListOf<RegionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.region_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return regionData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = regionData[position]
        holder.setRegionData(data)

        holder.itemView.region_item.setOnClickListener {
            if (holder.itemView.region_item.isSelected) {
                holder.itemView.region_item.isSelected = false
                holder.itemView.region_item.setTextColor(Color.parseColor("#555555"))
            } else {
                holder.itemView.region_item.isSelected = true
                holder.itemView.region_item.setTextColor(Color.parseColor("#ffffff"))
            }

            val intent = Intent(holder.itemView.context, GetRegionActivity::class.java)
            intent.putExtra("user_region", holder.itemView.region_item.text.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setRegionData(regionData: RegionData) {
        itemView.region_item.text = regionData.region_title
    }

}