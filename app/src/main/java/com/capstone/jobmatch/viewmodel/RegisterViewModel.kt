package com.capstone.jobmatch.viewmodel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class RegisterViewModel : ViewModel() {
    private val mAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore


    private fun saveUserToFirestore(email: String, phone: String, nama: String) {
        val user = hashMapOf(
            "email" to email,
            "phone" to phone,
            "nama" to nama
        )
        db.collection("Users").document(mAuth.currentUser?.uid.toString())
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun registerNewUser(view: View, binding: FragmentRegisterBinding, context: Context) {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val phone = binding.etPhone.text.toString()
        val nama = binding.etNama.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "Please enter phone!", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(nama)) {
            Toast.makeText(context, "Please enter nama!", Toast.LENGTH_LONG).show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(
                context,
                "Password too short, enter minimum 6 characters!",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(context, "Password not match!", Toast.LENGTH_LONG).show()
            return
        }
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { p0 ->
                    saveUserToFirestore(email, phone, nama)
                    if (p0.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Registration successful!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(
                            context,
                            "Registration failed! Please try again later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}