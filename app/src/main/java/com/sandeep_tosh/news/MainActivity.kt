package com.sandeep_tosh.news

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity() {

    lateinit var presenter: NewsPresenter
    var viewModel: NewsViewModel? = null
    var recyclerView: RecyclerView? = null
    var newsAdapter: NewsRVAdapter? = null
    var shimmerLayout: LinearLayout? = null
    var page = 1
    var isCountry = true
    var value = "us"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = NewsPresenter(this, this)

        var toolbar: Toolbar = findViewById(R.id.toolbar)
        var toolbarTextView: TextView = findViewById(R.id.toolbar_title)
        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        recyclerView = findViewById(R.id.recycler_view)
        shimmerLayout = findViewById(R.id.shimmer)

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

        newsAdapter = NewsRVAdapter(emptyList(), this, object : OnloadMoreListner {
            override fun onLoadMore() {
                presenter.loadData(++page, isCountry, value)
            }
        })
        recyclerView?.adapter = newsAdapter
        if(savedInstanceState==null) {
            presenter.loadData(page, isCountry, value)
        }else{
            viewModel?.list?.let { updateView(it) }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val chipGroup: ChipGroup = view.findViewById(R.id.chip_group)
        chipGroup.setOnCheckedChangeListener { chipGroup, i ->
            if ((i) != View.NO_ID) {
                val chip: Chip = chipGroup.findViewById(i)

                if (chip != null) {
                    if(isNetworkConnectionAvailable(this)) {
                        showLoading()
                        if(chip.text.equals("America")) {
                            presenter.loadData(1, true, "us")
                        }else if(chip.text.equals("United Arab Emirates")) {
                            presenter.loadData(1, true, "ae")
                        }else if(chip.text.equals("Argentina")) {
                            presenter.loadData(1, true, "ar")
                        }else{
                            presenter.loadData(1, true, "us")
                        }
                    }
                    dialog.dismiss()
                }
            }
        }

        val catergeroyChipGroup: ChipGroup = view.findViewById(R.id.catergeroy_chip_group)
        catergeroyChipGroup.setOnCheckedChangeListener { chipGroup, i ->
            if ((i) != View.NO_ID) {
                val chip: Chip = chipGroup.findViewById(i)
                if (chip != null) {
                    if(isNetworkConnectionAvailable(this)) {
                        showLoading()
                        presenter.loadData(1, false, chip.text as String)
                    }
                    dialog.dismiss()
                }

            }
        }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.filter) {

            showBottomSheetDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideLoading() {

        shimmerLayout?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
    }

    fun showLoading() {

        shimmerLayout?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE

    }

    fun updateView(response: List<NewsEntity>) {

        if (response.size == page * 20) {
            newsAdapter?.setLoadMore(true)
        } else {
            newsAdapter?.setLoadMore(false)
        }
        viewModel?.list=response
        hideLoading()
        newsAdapter?.updateList(response)


    }

    fun isNetworkConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
