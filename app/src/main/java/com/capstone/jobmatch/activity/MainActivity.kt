package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val intent = Intent(this, JobstreetActivity::class.java)
            intent.putExtra("result", binding.autoCompleteTextView.text.toString())
            startActivity(intent)
        }
        db = FirebaseFirestore.getInstance()
//        db.collection("Users").document(mAuth.currentUser?.uid.toString()).get().addOnSuccessListener {
//            binding.tvUserName.text = it.getString("nama")
//            binding.tvPhone.text = it.getString("phone")
//            binding.tvEmail.text = it.getString("email")
//        }

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
        }
    }

    private fun getLatestResult() {
        db.collection("Users").document(mAuth.currentUser?.uid.toString()).collection("JobHistory")
            .orderBy("date")
            .addSnapshotListener { value, error ->

                if (value != null) {
                    val result = value.documents.last().getString("result")
                    val array = convertStringToArray(result.toString())
                    DropdDown.init(this, binding.autoCompleteTextView, array)


                }

            }
    }


    override fun onResume() {
        super.onResume()
        getLatestResult()
    }
}