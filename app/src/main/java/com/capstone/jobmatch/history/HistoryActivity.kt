package com.capstone.jobmatch.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jobmatch.R
import com.capstone.jobmatch.core.model.History
import com.capstone.jobmatch.core.ui.HistoryAdapter
import com.capstone.jobmatch.databinding.ActivityHistoryBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.koin.android.ext.android.inject

class HistoryActivity() : AppCompatActivity(){
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    val viewModel by inject<HistoryViewModel>()
    private val historyRef = viewModel.getHistoryQuery()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = setupRecyclerView(binding.rvHistory, this)

    }


    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        context: Context,
        query: Query = historyRef,
    ): HistoryAdapter {
        val options = FirestoreRecyclerOptions.Builder<History>()
            .setQuery(query, History::class.java)
            .build()
        val adapter = HistoryAdapter(options)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        return adapter
    }




}
