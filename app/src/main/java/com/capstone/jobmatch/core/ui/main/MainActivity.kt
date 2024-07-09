package com.capstone.jobmatch.core.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.databinding.ActivityMainBinding
import com.capstone.jobmatch.core.ui.history.HistoryActivity
import com.capstone.jobmatch.core.ui.joma.JomaActivity
import com.capstone.jobmatch.core.ui.login.LoginActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by inject<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.logout.setOnClickListener {
            viewModel.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.ivLatestResult.setOnClickListener {
            val job = binding.autoCompleteTextView.text.toString()
            val uri = "https://www.jobstreet.co.id/id/job-search/$job-jobs/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }



        binding.joma.setOnClickListener {
            startActivity(Intent(this, JomaActivity::class.java))

        }

        binding.ivHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getProfile(binding)
        viewModel.getLatestResult(this,binding)
    }

}
