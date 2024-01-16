package com.capstone.jobmatch.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import com.capstone.jobmatch.activity.ResultActivity
import com.capstone.jobmatch.api.Repository
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.databinding.ActivityResultBinding
import com.capstone.jobmatch.model.PredictResponse
import com.capstone.jobmatch.model.PredictionRequest
import com.capstone.jobmatch.utills.ArrayConverter
import com.capstone.jobmatch.utills.DateFormatter
import com.capstone.jobmatch.utills.DropdDown
import com.capstone.jobmatch.utills.KeySkills
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JomaViewModel(application: Application,val repository: Repository) : AndroidViewModel(application) {


    private var skillsArray = KeySkills.arraySortByName
    private var selectedSkills: BooleanArray = BooleanArray(skillsArray.size)
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()


    fun predict(
        context: Context,
        binding: ActivityJomaBinding,
        degree: String,
        tahun: Int,
        key: String
    ) {
        val request = PredictionRequest(degree, tahun, key)
        binding.progressBar.visibility = View.VISIBLE
        repository.predict(request).enqueue(object : Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    binding.progressBar.visibility = View.GONE
                    val label = result?.label // Get the label from the response
                    saveHistoryToFirestore(
                        degree,
                        tahun.toString(),
                        key,
                        label.toString(),
                    )
                    val array = ArrayConverter.convertStringToArray(label.toString())
                    DropdDown.init(context, binding.autoCompleteTextView, array)
                    Log.d("Main", "onResponse: $label")
                    val intent = Intent(context, ResultActivity::class.java)
                    intent.putExtra("jurusan", degree)
                    intent.putExtra("tahun", tahun)
                    intent.putExtra("skills", key)
                    intent.putExtra("result", label)
                    startActivity(context, intent, null)
                } else {
                    binding.progressBar.visibility = View.GONE
                    DropdDown.init(context, binding.autoCompleteTextView, arrayOf("Error"))
                    Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                    Log.d("Main", "onResponse Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                Log.d("Main", "onFailure: ${t.message}")
            }
        })
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

    fun showSkillsDialog(context: Context, intent: Intent, skillsList: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)

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
            setPositiveButton("OK") { dialog, which ->
                for (i in selectedSkills.indices) {
                    val checked = selectedSkills[i]
                    if (checked) {
                        intent.putExtra("skills", skillsList)
                    }
                }
            }

            setNeutralButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            builder
                .create()
                .show()
        }

    }

}