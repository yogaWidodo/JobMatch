package com.capstone.jobmatch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jobmatch.activity.JobstreetActivity
import com.capstone.jobmatch.model.History
import com.capstone.jobmatch.utills.ArrayConverter
import com.capstone.jobmatch.utills.DropdDown
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class HistoryAdapter(options: FirestoreRecyclerOptions<History>) :
    FirestoreRecyclerAdapter<History, HistoryAdapter.HistoryViewHolder>(options) {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jurusan: TextView = itemView.findViewById(R.id.tvJurusan)
        val tahunKerja: TextView = itemView.findViewById(R.id.tvPengalamanKerja)
        val skills: TextView = itemView.findViewById(R.id.tvSkills)
        val result: AutoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView)
        val buttonSearch: Button = itemView.findViewById(R.id.btnJobStreet)
        val date: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item_list, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int, model: History) {
        holder.apply {
            jurusan.text = model.jurusan
            tahunKerja.text = model.pengalaman
            skills.text = model.skills
            date.text = model.date
            val array = ArrayConverter.convertStringToArray(model.skills)
            DropdDown.init(itemView.context, result, array)
            buttonSearch.setOnClickListener {
                val intent = Intent(itemView.context, JobstreetActivity::class.java)
                intent.putExtra("result", result.text.toString())
                startActivity(itemView.context, intent, null)
            }
        }
    }


}


//
//class HistoryAdapter(val context: Context) :
//    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
//    private val fullList = ArrayList<History>()
//    private val listHistory = ArrayList<History>()
//
//
//    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val jurusan: TextView = itemView.findViewById(R.id.tvJurusan)
//        val tahunKerja: TextView = itemView.findViewById(R.id.tvPengalamanKerja)
//        val skills: TextView = itemView.findViewById(R.id.tvSkills)
//        val result: AutoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView)
//        val buttonSearch: Button = itemView.findViewById(R.id.btnJobStreet)
//        val date: TextView = itemView.findViewById(R.id.tvDate)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.history_item_list, parent, false)
//        return HistoryViewHolder(view)
//    }
//
//    override fun getItemCount(): Int = listHistory.size
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
//        val history = listHistory[position]
//        holder.apply {
//            jurusan.text = history.jurusan
//            tahunKerja.text = history.tahunKerja
//            skills.text = history.skills
//            date.text = history.date
//            val array = ArrayConverter.convertStringToArray(history.skills)
//            DropdDown.init(context, result, array)
//            buttonSearch.setOnClickListener {
//                val intent = Intent(context, JobstreetActivity::class.java)
//                intent.putExtra("result", result.text.toString())
//                startActivity(context, intent, null)
//            }
//        }
//    }
//
//
//    fun updateList(newList: List<History>) {
//        fullList.clear()
//        fullList.addAll(newList)
//        listHistory.clear()
//        listHistory.addAll(fullList)
//        notifyDataSetChanged()
//    }
//
//}