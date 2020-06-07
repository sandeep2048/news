package com.sandeep_tosh.news

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import android.util.Pair as APair

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
            .into(newsHolder.image)

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

        newsHolder.parent.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("title",list.get(position).title)
            intent.putExtra("url",list.get(position).url)
            intent.putExtra("date",list.get(position).date)
            intent.putExtra("publisher",list.get(position).publisher)
            intent.putExtra("desc",list.get(position).content)

            var t1 = APair(newsHolder.tilte as View, "title")
            var t2 = APair(newsHolder.date as View, "date")
            var t3 = APair(newsHolder.publisher as View, "publisher")

            val transitionActivityOptions: ActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity,
                    t1,t2,t3
                )


            context.startActivity(intent,transitionActivityOptions.toBundle())
        })
    }


    class NewsViewHolder(itemView: View) : ViewHolder(itemView) {

        lateinit var tilte: TextView
        lateinit var date: TextView
        lateinit var publisher: TextView
        var image:ImageView
        var parent:ConstraintLayout

        init {
             tilte = itemView.findViewById(R.id.title)
             date = itemView.findViewById(R.id.date)
             publisher = itemView.findViewById(R.id.publisher)
            image=itemView.findViewById(R.id.image)
            parent=itemView.findViewById(R.id.parent)
        }
        companion object
    }

}