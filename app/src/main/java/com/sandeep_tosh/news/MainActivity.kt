package com.sandeep_tosh.news

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    lateinit var presenter: NewsPresenter
    var viewModel: NewsViewModel? = null
    var recyclerView: RecyclerView? = null
    var newsAdapter:NewsRVAdapter?=null
    var shimmerLayout:LinearLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = NewsPresenter(this,this)

        var toolbar: Toolbar = findViewById(R.id.toolbar)
        var toolbarTextView: TextView = findViewById(R.id.toolbar_title)
        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        recyclerView = findViewById(R.id.recycler_view)
        shimmerLayout=findViewById(R.id.shimmer)

        setSupportActionBar(toolbar)
        toolbarTextView.text = "HEADLINES";
        toolbar.title = ""

        val roboto = Typeface.createFromAsset(
            this.getAssets(),
            "fonts/RobotoSlab-Bold.ttf"
        )

        toolbarTextView.typeface = roboto


        viewModel = ViewModelProviders.of(this)
            .get<NewsViewModel>(NewsViewModel::class.java)

        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.setLayoutManager(mLayoutManager)

        newsAdapter= NewsRVAdapter(emptyList(),this)
        recyclerView?.adapter=newsAdapter
        presenter.loadData()


    }

    fun hideLoading(){

            shimmerLayout?.visibility= View.GONE
            recyclerView?.visibility= View.VISIBLE
    }
    fun updateView(response: List<NewsEntity>) {

        var parser = NewsParser()
      //  var list = parser.parseResponse(response)
        hideLoading()

        newsAdapter?.updateList(response)
      //  newsAdapter?.notifyDataSetChanged()


    }
}
