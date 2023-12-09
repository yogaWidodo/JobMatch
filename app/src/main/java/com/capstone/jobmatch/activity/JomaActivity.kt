package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.utills.DropdDown

class JomaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJomaBinding
    private lateinit var intent: Intent
    private var selectedSkills: BooleanArray = BooleanArray(19)
    private var skillsList: ArrayList<String> = arrayListOf()
    private var skillsArray: Array<String> = arrayOf(
        "Media Planning",
        "pre sales",
        "Computer science",
        "Technical Support",
        "manual testing",
        "adobe experience manager",
        "channel partners",
        "TFS",
        "Bde",
        "technical support",
        "secretary",
        "website",
        "operations",
        "Oracle IDAM",
        "digital marketing",
        "instrumentation engineering",
        "Report Generation",
        "Copyright",
        "C#"
    )
    private lateinit var jurusanArray: Array<out String>
    private lateinit var autoCompleteTextView: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJomaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jurusanArray = resources.getStringArray(R.array.jurusan)
        intent = Intent(this, ResultActivity::class.java)

        binding.cardSkills.setOnClickListener {
            showSkillsDialog()
        }
        autoCompleteTextView = binding.autoCompleteTextView

        val tahunKerja = binding.etTahunKerja.text

        binding.btnSubmit.setOnClickListener {
            intent.putExtra("jurusan", autoCompleteTextView.text.toString())
            intent.putExtra("tahunKerja", tahunKerja.toString())
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        DropdDown.init(this, autoCompleteTextView, jurusanArray)
    }

    private fun showSkillsDialog() {
        val builder = AlertDialog.Builder(this)

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