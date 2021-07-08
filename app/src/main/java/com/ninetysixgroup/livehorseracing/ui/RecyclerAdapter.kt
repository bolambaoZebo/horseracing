package com.ninetysixgroup.livehorseracing.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ninetysixgroup.livehorseracing.R
import com.ninetysixgroup.livehorseracing.api.HorseModel
import kotlinx.android.synthetic.main.home_cardview.view.*

class  RecyclerAdapter(
    val context: Context, val items: ArrayList<HorseModel>, val typeView: Int,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    companion object {
        const val VIEW_TYPE_ONE = 0
        const val VIEW_TYPE_TWO = 1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder1(
            LayoutInflater.from(context).inflate(R.layout.home_cardview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items.get(position)

        if(holder is ViewHolder1){
            holder.title.text = item.channelName
            holder.image.setImageResource(item.image)
            holder.image.setImageResource(item.image)
            holder.dateRace.text = item.racingDate
            holder.timeRace.text = item.racingTime

            holder.cardView.setOnClickListener({
//                Toast.makeText(context, item.id, Toast.LENGTH_SHORT).show()
                listener.onClickItem(position, "US",item.id,context)
            })
        }
    }

    private inner class ViewHolder1(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var image = view.home_cardview_image
        var title = view.home_cardview_title
        var dateRace = view.home_cardview_date
        var timeRace = view.home_cardview_time
        var cardView = view.home_cardview

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION){
            }
        }

        init {
            view.setOnClickListener(this)
        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onClickItem(position: Int,title: String,channel: String,context: Context)
    }
}