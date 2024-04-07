package com.capstone.jobmatch

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.jobmatch.databinding.ActivityMainBinding
import com.capstone.jobmatch.history.HistoryActivity
import com.capstone.jobmatch.joma.JomaActivity
import com.capstone.jobmatch.login.LoginActivity
import com.capstone.jobmatch.core.utills.ArrayConverter.convertStringToArray
import com.capstone.jobmatch.core.utills.DropdDown
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
