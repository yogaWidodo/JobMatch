package com.capstone.jobmatch.activity

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.jobmatch.databinding.ActivityMainBinding
import com.capstone.jobmatch.utills.ArrayConverter.convertStringToArray
import com.capstone.jobmatch.utills.DropdDown
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.ivLatestResult.setOnClickListener {
            val job = binding.autoCompleteTextView.text.toString()
            val uri = "https://www.jobstreet.co.id/id/job-search/$job-jobs/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
        db = FirebaseFirestore.getInstance()
        db.collection("Users").document(mAuth.currentUser?.uid.toString())
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    binding.tvUserName.text = value.getString("nama")
                    binding.tvPhone.text = value.getString("phone")
                    binding.tvEmail.text = value.getString("email")
                }
            }


        binding.joma.setOnClickListener {
            startActivity(Intent(this, JomaActivity::class.java))

        }

        binding.ivHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
            getLatestResult()
        }
        getLatestResult()
    }

    private fun getLatestResult() {
        db.collection("Users").document(mAuth.currentUser?.uid.toString())
            .collection("JobHistory")
            .orderBy("date")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("Main", "getLatestResult: ${error.message}")
                }
                if (value == null || value.isEmpty) {
                    Log.d("Main", "getLatestResult: No Data")
                } else {
                    val documents = value.documents
                    if (documents.isNotEmpty()) {
                        val result = documents.last().getString("result").toString()
                        val array = convertStringToArray(result)
                        DropdDown.init(this, binding.autoCompleteTextView, array)
                    }
                }
            }
    }
}
