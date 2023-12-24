package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.databinding.ActivityResultBinding
import com.capstone.jobmatch.utills.ArrayConverter
import com.capstone.jobmatch.utills.DropdDown

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jurusan = intent.getStringExtra("jurusan").toString()
        val tahunKerja = intent.getStringExtra("tahun")
        val skills = intent.getStringArrayListExtra("skills")
        val result = intent.getStringExtra("result")
        val autoComplete = binding.autoCompleteTextView

        val array = ArrayConverter.convertStringToArray(result.toString())

        binding.tvSkills.text = skills.toString()
        binding.tvProgramStudi.text = jurusan
        binding.tvPengalamanKerja.text = tahunKerja

        DropdDown.init(this, autoComplete, array)

        binding.btnJobStreet.setOnClickListener {
            val intent = Intent(this, JobstreetActivity::class.java)
            intent.putExtra("result", autoComplete.text)
            startActivity(intent)
        }

    }





}