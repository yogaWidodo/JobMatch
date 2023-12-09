package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jobmatch.databinding.ActivityResultBinding
import com.capstone.jobmatch.utills.DropdDown

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val prgramStudi = intent.getStringExtra("jurusan").toString()
        val pengalamanKerja = intent.getStringExtra("tahunKerja")
        val skills = intent.getStringArrayListExtra("skills")
        val autoComplete = binding.autoCompleteTextView


        val array: Array<out String> = arrayOf(prgramStudi)
        binding.tvSkills.text = skills.toString()
        binding.tvProgramStudi.text = prgramStudi
        binding.tvPengalamanKerja.text = pengalamanKerja

        DropdDown.init(this, autoComplete, array)

        binding.btnJobStreet.setOnClickListener {
            val intent = Intent(this, JobstreetActivity::class.java)
            intent.putExtra("result", autoComplete.text.toString())
            startActivity(intent)
        }

    }
}