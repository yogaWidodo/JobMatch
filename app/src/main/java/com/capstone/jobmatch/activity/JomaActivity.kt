package com.capstone.jobmatch.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.viewModels
import com.capstone.jobmatch.R
import com.capstone.jobmatch.databinding.ActivityJomaBinding
import com.capstone.jobmatch.utills.DropdDown
import com.capstone.jobmatch.viewmodel.JomaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class JomaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJomaBinding
    private val viewModel by viewModels<JomaViewModel>()
    private var skillsList: ArrayList<String> = arrayListOf()
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var tahunKerja: EditText
    private lateinit var jurusanArray: Array<String>
    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

//    private val tflite by lazy {
//        Interpreter(FileUtil.loadMappedFile(this, MODEL_PATH))
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJomaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
//        try {
//            var pred = doInference()
//
//            var cb = pred.get("prediction")
//            Toast.makeText(this, cb?.get(0).toString(), Toast.LENGTH_SHORT).show()
//
//            Log.i("PREDICTION: ", cb?.get(0).toString())
//        }catch(e: Exception){
//            Log.e("ERR: ", e.toString())
//        }


        binding.cardSkills.setOnClickListener {
            viewModel.showSkillsDialog(this, intent, skillsList)
        }

        binding.btnSubmit.setOnClickListener {
            binding.progressBar.visibility= View.VISIBLE
            insertHistory()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("jurusan", autoCompleteTextView.text.toString())
            intent.putExtra("tahun", tahunKerja.text.toString())
            intent.putExtra("skills", skillsList)
            intent.putExtra("result", skillsList.toString())
            startActivity(intent)
            finish()
        }


    }

    override fun onResume() {
        super.onResume()
        DropdDown.init(this, autoCompleteTextView, jurusanArray)
    }


    private fun insertHistory() {

//        val history = History(
//            0,
//            autoCompleteTextView.text.toString(),
//            tahunKerja.text.toString(),
//            skillsList.toString(),
//            skillsList.toString(),
//            DateFormatter.formatDate()
//        )
        viewModel.saveHistoryToFirestore(
            autoCompleteTextView.text.toString(),
            tahunKerja.text.toString(),
            skillsList.toString(),
            binding
        )
//        viewModel.insertHistory(history)

    }


    private fun initUI() {
        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        tahunKerja = binding.etTahunKerja
        autoCompleteTextView = binding.autoCompleteTextView
        jurusanArray = resources.getStringArray(R.array.jurusan)
    }




//    private fun floatArrayToBuffer(floatArray: FloatArray): FloatBuffer {
//        val byteBuffer: ByteBuffer = ByteBuffer
//            .allocateDirect(floatArray.size * 4)
//
//        byteBuffer.order(ByteOrder.nativeOrder())
//
//        val floatBuffer: FloatBuffer = byteBuffer.asFloatBuffer()
//
//        floatBuffer.put(floatArray)
//        floatBuffer.position(0)
//        return floatBuffer
//    }
//
//    private fun  doInference():Map<String,FloatBuffer?>{
//        val input = floatArrayOf(63.0F, 1.0F, 3.0F, 145.0F , 233.0F , 1.0F, 0.0F, 150.0F, 0.0F, 2.3F, 0.0F, 0.0F, 1.0F)
//
//        val inF = floatArrayToBuffer(input)
//
//        var outs = floatArrayOf(0.0F)
//
//        var outF = floatArrayToBuffer(outs)
//
//        val inputs: Map<String, FloatBuffer?> = mapOf("x" to inF)
//
//        var outputs: Map<String, FloatBuffer?> = mutableMapOf("prediction" to outF)
//
//        tflite.run(inputs, outputs)
//        return mapOf("prediction" to outF)
//    }
//
//
//    companion object {
//        private const val MODEL_PATH = "model.tflite"
//    }


}