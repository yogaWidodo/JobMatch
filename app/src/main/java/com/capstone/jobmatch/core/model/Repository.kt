package com.capstone.jobmatch.core.model

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.capstone.jobmatch.R
import com.capstone.jobmatch.core.api.ApiInterface
import com.capstone.jobmatch.core.utills.ArrayConverter
import com.capstone.jobmatch.core.utills.DateFormatter
import com.capstone.jobmatch.core.utills.DropdDown
import com.capstone.jobmatch.core.utills.KeySkills
import com.capstone.jobmatch.databinding.ActivityMainBinding
import com.capstone.jobmatch.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import retrofit2.Call

class Repository(
    private val myApi: ApiInterface,
    private val mAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) {


    fun getHistoryRef(): Query {
        return db.collection("Users").document(mAuth.currentUser!!.uid).collection("JobHistory")
            .orderBy("date")
    }

    fun predict(request: PredictionRequest): Call<PredictResponse> {
        return myApi.predict(request)
    }

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


    fun saveHistoryToFirestore(
        jurusan: String,
        pengalaman: String,
        skills: String,
        result: String,
    ) {
        val history = hashMapOf(
            "jurusan" to jurusan,
            "pengalaman" to pengalaman,
            "skills" to skills,
            "result" to result,
            "date" to DateFormatter.formatDate()
        )
        db.collection("Users").document(mAuth.currentUser!!.uid).collection("JobHistory").document()
            .set(history)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun sendResetPasswordEmail(email: String) {
        mAuth.sendPasswordResetEmail(email)
    }


    fun registerNewUser(view: View, binding: FragmentRegisterBinding, context: Context) {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val phone = binding.etPhone.text.toString()
        val nama = binding.etNama.text.toString()

        val (isValid, errorMessage) = validateInput(email, password, confirmPassword, phone, nama)

        if (!isValid) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    saveUserToFirestore(email, phone, nama)
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
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

    private fun validateInput(
        email: String,
        password: String,
        confirmPassword: String,
        phone: String,
        nama: String
    ): Pair<Boolean, String> {
        return when {
            TextUtils.isEmpty(email) -> Pair(false, "Please enter email...")
            TextUtils.isEmpty(password) -> Pair(false, "Please enter password!")
            TextUtils.isEmpty(phone) -> Pair(false, "Please enter phone!")
            TextUtils.isEmpty(nama) -> Pair(false, "Please enter nama!")
            password.length < 6 -> Pair(false, "Password too short, enter minimum 6 characters!")
            password != confirmPassword -> Pair(false, "Password not match!")
            else -> Pair(true, "")
        }
    }


    fun showSkillsDialog(context: Context, intent: Intent, skillsList: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val skillsArray = KeySkills.arraySortByName
        val selectedSkills = BooleanArray(skillsArray.size)

        builder.apply {
            setTitle("Select Skills")
            setCancelable(false)
            setMultiChoiceItems(skillsArray, selectedSkills) { _, which, isChecked ->
                selectedSkills[which] = isChecked
                val skill = skillsArray[which]
                if (isChecked) {
                    skillsList.add(skill)
                } else {
                    skillsList.remove(skill)
                }
            }
            setPositiveButton("OK") { _, _ ->
                for (i in selectedSkills.indices) {
                    val checked = selectedSkills[i]
                    if (checked) {
                        intent.putExtra("skills", skillsList)
                    }
                }
            }

            setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder
                .create()
                .show()
        }
    }


    fun signOut() {
        mAuth.signOut()
    }



    fun getProfile(binding: ActivityMainBinding){
        db.collection("Users").document(mAuth.currentUser?.uid.toString())
            .addSnapshotListener { value, _ ->
                if (value != null) {
                    binding.tvUserName.text = value.getString("nama")
                    binding.tvPhone.text = value.getString("phone")
                    binding.tvEmail.text = value.getString("email")
                }
            }
    }


     fun getLatestResult(context: Context, binding: ActivityMainBinding) {
        db.collection("Users").document(mAuth.currentUser?.uid.toString())
            .collection("JobHistory")
            .orderBy("date")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("Main", "getLatestResult: ${error.message}")
                }
                if (value == null || value.isEmpty) {
                    Log.d("Main", "getLatestResult: No Data")
                } else {
                    val documents = value.documents
                    if (documents.isNotEmpty()) {
                        val result = documents.last().getString("result").toString()
                        val array = ArrayConverter.convertStringToArray(result)
                        DropdDown.init(context, binding.autoCompleteTextView, array)
                    }
                }
            }
    }
}
