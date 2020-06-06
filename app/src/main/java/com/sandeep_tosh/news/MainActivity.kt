package com.sandeep_tosh.news

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var toolbar: Toolbar = findViewById(R.id.toolbar)
        var toolbarTextView: TextView = findViewById(R.id.toolbar_title)
        getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        setSupportActionBar(toolbar)
        toolbarTextView.text = "HEADLINES";
        toolbar.title = ""

        val roboto = Typeface.createFromAsset(
            this.getAssets(),
            "fonts/RobotoSlab-Bold.ttf"
        )

        toolbarTextView.typeface = roboto


    }
}
