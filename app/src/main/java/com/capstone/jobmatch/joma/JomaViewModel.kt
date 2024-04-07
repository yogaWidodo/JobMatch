package com.capstone.jobmatch.joma

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.capstone.jobmatch.result.ResultActivity
import com.capstone.jobmatch.core.model.Repository
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.core.model.PredictResponse
import com.capstone.jobmatch.core.model.PredictionRequest
import com.capstone.jobmatch.core.utills.ArrayConverter
import com.capstone.jobmatch.core.utills.DropdDown
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JomaViewModel(private val repository: Repository) : ViewModel() {


    fun predict(
        context: Context,
        binding: ActivityJomaBinding,
        degree: String,
        tahun: Int,
        key: String
    ) {
        val request = PredictionRequest(degree, tahun, key)
        showProgressBar(binding, true)
        repository.predict(request).enqueue(object :
            Callback<PredictResponse> {
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
            ) {
                showProgressBar(binding, false)
                val label = response.body()?.label
                saveHistoryToFirestore(degree, tahun.toString(), key, label.toString())
                val array = ArrayConverter.convertStringToArray(label.toString())
                DropdDown.init(context, binding.autoCompleteTextView, array)
                val intent = Intent(context, ResultActivity::class.java)
                intent.putExtra("jurusan", degree)
                intent.putExtra("tahun", tahun)
                intent.putExtra("skills", key)
                intent.putExtra("result", label)
                startActivity(context, intent, null)

            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                showProgressBar(binding, false)
                DropdDown.init(context, binding.autoCompleteTextView, arrayOf("Error"))
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveHistoryToFirestore(
        jurusan: String,
        pengalaman: String,
        skills: String,
        result: String
    ) =
        repository.saveHistoryToFirestore(jurusan, pengalaman, skills, result)


    fun showSkillsDialog(context: Context, intent: Intent, skillsList: ArrayList<String>) {
        repository.showSkillsDialog(context, intent, skillsList)
    }


    private fun showProgressBar(binding: ActivityJomaBinding, boolean: Boolean) {
        if (boolean) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}