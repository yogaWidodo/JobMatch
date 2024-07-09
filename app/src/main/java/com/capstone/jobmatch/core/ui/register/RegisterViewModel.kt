package com.capstone.jobmatch.core.ui.register

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.capstone.jobmatch.core.model.Repository
import com.capstone.jobmatch.databinding.FragmentRegisterBinding

class RegisterViewModel(private val repository: Repository) : ViewModel() {


    fun registerNewUser(view: View, binding: FragmentRegisterBinding, context: Context) =
        repository.registerNewUser(view, binding, context)
}