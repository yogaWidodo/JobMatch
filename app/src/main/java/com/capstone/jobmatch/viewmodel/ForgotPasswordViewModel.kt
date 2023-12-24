package com.capstone.jobmatch.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel: ViewModel() {
    private val mAuth = FirebaseAuth.getInstance()


    fun sendResetPasswordEmail(email: String) {
        mAuth.sendPasswordResetEmail(email)
    }
}