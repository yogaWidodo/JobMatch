package com.capstone.jobmatch.core.ui.history

import androidx.lifecycle.ViewModel
import com.capstone.jobmatch.core.model.History
import com.capstone.jobmatch.core.model.Repository
import com.capstone.jobmatch.core.ui.HistoryAdapter


class HistoryViewModel(private val repository: Repository) : ViewModel() {
    fun getHistoryQuery() = repository.getHistoryRef()

}