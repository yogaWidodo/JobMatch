package com.capstone.jobmatch.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.jobmatch.api.Repository
import com.google.android.play.integrity.internal.aa

class ViewModelFactory (private val repository: Repository,private val application: Application):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(JomaViewModel::class.java) -> {
                JomaViewModel(application,repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel " + modelClass.name)
        }
    }

}