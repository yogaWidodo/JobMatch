package com.capstone.jobmatch.activity

import android.content.Intent
import android.net.Uri
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
        val tahunKerja = intent.getIntExtra("tahun",0)
        val skills = intent.getStringExtra("skills")
        val result = intent.getStringArrayListExtra("result")
        val autoComplete = binding.autoCompleteTextView
        val array = ArrayConverter.convertStringToArray(result.toString())

        binding.tvSkills.text = skills.toString()
        binding.tvProgramStudi.text = jurusan
        binding.tvPengalamanKerja.text = tahunKerja.toString()

        DropdDown.init(this, autoComplete, array)
        binding.btnJobStreet.setOnClickListener {
            val job = autoComplete.text.toString()
            val uri = "https://www.jobstreet.co.id/id/job-search/$job-jobs/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }
    }
}

