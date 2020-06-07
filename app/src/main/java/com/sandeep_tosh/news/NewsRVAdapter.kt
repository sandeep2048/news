package com.sandeep_tosh.news

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso


class NewsRVAdapter(var list: List<NewsEntity>, val context:Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.news_item_view, parent, false)
        )
    }

    fun updateList(list: List<NewsEntity>){
        this.list=list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       var newsHolder:NewsViewHolder = holder as NewsViewHolder

        Picasso.with(context)
            .load(list.get(position).url).fit()
            .into(newsHolder.parent)
      //  newsHolder.parent.scrollTo();
       // newsHolder.parent.setHorizontalFadingEdgeEnabled(true);
       // newsHolder.parent.fade(40);
        newsHolder.date.text=list.get(position).date
        newsHolder.publisher.text=list.get(position).publisher
        newsHolder.tilte.text=list.get(position).title

        val roboto = Typeface.createFromAsset(
            context.getAssets(),
            "fonts/RobotoSlab-Bold.ttf"
        )

        newsHolder.publisher.typeface = roboto

        val robotoreg = Typeface.createFromAsset(
            context.getAssets(),
            "fonts/RobotoSlab-Regular.ttf"
        )

        newsHolder.tilte.typeface = robotoreg
    }


    class NewsViewHolder(itemView: View) : ViewHolder(itemView) {

        lateinit var tilte: TextView
        lateinit var date: TextView
        lateinit var publisher: TextView
        var parent:ImageView

        init {
             tilte = itemView.findViewById(R.id.title)
             date = itemView.findViewById(R.id.date)
             publisher = itemView.findViewById(R.id.publisher)
            parent=itemView.findViewById(R.id.parent)
        }
        companion object
    }

}