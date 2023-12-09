package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.ActivityJobstreetBinding

class JobstreetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobstreetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobstreetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra("result")
        binding.webView.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://www.jobstreet.co.id/id/job-search/$result-jobs/")
        }
    }
}