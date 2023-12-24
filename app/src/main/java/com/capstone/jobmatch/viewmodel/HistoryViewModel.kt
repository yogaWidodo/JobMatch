package com.capstone.jobmatch.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jobmatch.HistoryAdapter
import com.capstone.jobmatch.model.History
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    //    val allHistory: LiveData<List<History>>
//    private val repository: HistoryRepository
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val historyRef =
        db.collection("Users").document(mAuth.currentUser!!.uid).collection("JobHistory")
            .orderBy("date")

//    init {
//        val dao = HistoryDatabase.getDatabase(application).historyDao()
//        repository = HistoryRepository(dao)
//        allHistory = repository.allHistory
//    }


    fun setupRecyclerView(
        recyclerView: RecyclerView,
        context: Context
    ): HistoryAdapter {
        val options = FirestoreRecyclerOptions.Builder<History>()
            .setQuery(historyRef, History::class.java)
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