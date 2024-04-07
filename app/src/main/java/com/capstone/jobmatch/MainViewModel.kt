package com.capstone.jobmatch

import android.content.Context
import androidx.lifecycle.ViewModel
import com.capstone.jobmatch.core.model.Repository
import com.capstone.jobmatch.databinding.ActivityMainBinding


class MainViewModel(private val repository: Repository) : ViewModel() {

    fun signOut() {
        repository.signOut()
    }

    fun getProfile(binding: ActivityMainBinding) {
        repository.getProfile(binding)
    }

    fun getLatestResult(context: Context, binding: ActivityMainBinding) {
        repository.getLatestResult(context, binding)
    }
}