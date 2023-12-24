package com.capstone.jobmatch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.capstone.jobmatch.HistoryAdapter
import com.capstone.jobmatch.databinding.ActivityHistoryBinding
import com.capstone.jobmatch.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel by viewModels<HistoryViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = viewModel.setupRecyclerView(binding.rvHistory, this)

//        viewModel.allHistory.observe(this) { list ->
//            list?.let {
//                adapter.updateList(list)
//            }
//        }
    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}
