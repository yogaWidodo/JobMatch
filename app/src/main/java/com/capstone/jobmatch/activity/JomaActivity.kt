package com.capstone.jobmatch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.jobmatch.R
import com.capstone.jobmatch.api.Repository
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.utills.ArrayConverter
import com.capstone.jobmatch.utills.DropdDown
import com.capstone.jobmatch.utills.Jurusan
import com.capstone.jobmatch.viewmodel.JomaViewModel
import com.capstone.jobmatch.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class JomaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJomaBinding
    private lateinit var viewModel: JomaViewModel
    private var skillsList: ArrayList<String> = arrayListOf()
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var tahunKerja: EditText
    private lateinit var jurusanArray: Array<String>
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

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
            if (autoCompleteTextView.text.isEmpty() || tahunKerja.text.isEmpty()|| skillsList.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.predict(this, binding, jurusanConvert, tahunKerja.text.toString().toInt(),skillString)
        }
    }

    override fun onResume() {
        super.onResume()
        DropdDown.init(this, autoCompleteTextView, jurusanArray)
    }
    private fun initUI() {
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        tahunKerja = binding.etTahunKerja
        autoCompleteTextView = binding.autoCompleteTextView
        jurusanArray = resources.getStringArray(R.array.jurusan)
        val repository = Repository()
        val viewModelFactory = ViewModelFactory(repository,application)
        viewModel = ViewModelProvider(this, viewModelFactory)[JomaViewModel::class.java]
    }

}