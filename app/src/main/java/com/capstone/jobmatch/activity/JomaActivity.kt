package com.capstone.jobmatch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.ActivityJomaBinding

class JomaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJomaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJomaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}