package com.capstone.jobmatch.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.utills.DateFormatter
import com.capstone.jobmatch.utills.KeySkills
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class JomaViewModel(application: Application) : AndroidViewModel(application) {

//    val allHistory: LiveData<List<History>>
//    private val repository: HistoryRepository
    private var skillsArray = KeySkills.arraySortByName
    private var selectedSkills: BooleanArray = BooleanArray(skillsArray.size)
    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

//    init {
//        val dao = HistoryDatabase.getDatabase(application).historyDao()
//        repository = HistoryRepository(dao)
//        allHistory = repository.allHistory
//    }

//    fun insertHistory(history: History) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insertHistory(history)
//    }


    fun saveHistoryToFirestore(
        jurusan: String,
        pengalaman: String,
        skills: String,
        binding:ActivityJomaBinding,
    ) {
        val history = hashMapOf(
            "jurusan" to jurusan,
            "pengalaman" to pengalaman,
            "skills" to skills,
            "result" to skills,
            "date" to DateFormatter.formatDate()
        )
        db.collection("Users").document(mAuth.currentUser!!.uid).collection("JobHistory").document()
            .set(history)
            .addOnSuccessListener { documentReference ->
                binding.progressBar.visibility= View.GONE
                Log.d("TAG", "DocumentSnapshot added with ID: $documentReference")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun showSkillsDialog(context: Context, intent: Intent,skillsList: ArrayList<String>) {
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