package com.app.newzpaper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var getStartedBtn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseLayouts()
        getStartedBtn.setOnClickListener(View.OnClickListener {
            launchNextActivity()
        })
    }

    private fun launchNextActivity() {
        val intent = Intent(this,NewsActivity::class.java)
        startActivity(intent)
    }

    private fun initialiseLayouts() {
        getStartedBtn = findViewById(R.id.getStartedBtn)
    }
}