package com.capstone.jobmatch.forgot

import androidx.lifecycle.ViewModel
import com.capstone.jobmatch.core.model.Repository
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(val repository: Repository) : ViewModel() {

    fun sendResetPasswordEmail(email: String) = repository.sendResetPasswordEmail(email)

}