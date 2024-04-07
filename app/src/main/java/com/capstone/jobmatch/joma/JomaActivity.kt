package com.capstone.jobmatch.joma

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import com.capstone.jobmatch.R
import com.capstone.jobmatch.core.model.PredictionRequest
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.core.utills.ArrayConverter
import com.capstone.jobmatch.core.utills.DropdDown
import com.capstone.jobmatch.core.utills.Jurusan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject


class JomaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJomaBinding
    private var skillsList: ArrayList<String> = arrayListOf()
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var tahunKerja: EditText
    private lateinit var jurusanArray: Array<String>

    val viewModel by inject<JomaViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJomaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()


        binding.cardSkills.setOnClickListener {
            viewModel.showSkillsDialog(this, intent, skillsList)
        }

        binding.btnSubmit.setOnClickListener {
            val skillString = ArrayConverter.removeBrackets(skillsList.toString())
            val jurusanConvert = Jurusan.dataJurusan(autoCompleteTextView.text.toString())
            if (autoCompleteTextView.text.isEmpty() || tahunKerja.text.isEmpty() || skillsList.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val query = PredictionRequest(
                jurusanConvert,
                tahunKerja.text.toString().toInt(),
                skillString
            )
            viewModel.predict(
                this,
                binding,
                query.degree,
                query.job,
                query.key
            )
        }
    }

    override fun onResume() {
        super.onResume()
        DropdDown.init(this, autoCompleteTextView, jurusanArray)
    }

    private fun initUI() {
        tahunKerja = binding.etTahunKerja
        autoCompleteTextView = binding.autoCompleteTextView
        jurusanArray = resources.getStringArray(R.array.jurusan)
    }

}