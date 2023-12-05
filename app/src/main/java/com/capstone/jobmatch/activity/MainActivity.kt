package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.joma.setOnClickListener {
            startActivity(Intent(this, JomaActivity::class.java))
        }

        binding.ivHistory.setOnClickListener{
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}