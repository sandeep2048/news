package com.sandeep_tosh.news

import android.app.Activity
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)


        var image:ImageView=findViewById(R.id.image)

        Picasso.with(this)
            .load( intent.getStringExtra("url")).fit()
            .into(image)

        var title:TextView=findViewById(R.id.title)
        title.text=intent.getStringExtra("title")

        var desc:TextView=findViewById(R.id.content)
        desc.text=intent.getStringExtra("desc")

        var date:TextView=findViewById(R.id.date)


            date.text=intent.getStringExtra("date").substring(0,intent.getStringExtra("date").indexOf("T"))



        var publisher:TextView=findViewById(R.id.publisher)
        publisher.text=intent.getStringExtra("publisher")

        var close:ImageView=findViewById(R.id.close)
        close.setOnClickListener(View.OnClickListener { onBackPressed() })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        var title:TextView=findViewById(R.id.title)
        title.textSize= 24F
       this.supportFinishAfterTransition()
    }
}
