package com.capstone.jobmatch.core.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.capstone.jobmatch.R
import com.capstone.jobmatch.core.model.History
import com.capstone.jobmatch.core.utills.ArrayConverter
import com.capstone.jobmatch.core.utills.DropdDown
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class HistoryAdapter(options: FirestoreRecyclerOptions<History>) :
    FirestoreRecyclerAdapter<History, HistoryAdapter.HistoryViewHolder>(options) {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val history_layout = itemView.findViewById<CardView>(R.id.card_layout)
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
        val currentHistory = getItem(position)
        holder.apply {
            jurusan.text = model.jurusan
            tahunKerja.text = model.pengalaman
            skills.text = model.skills
            date.text = model.date
            val array = ArrayConverter.convertStringToArray(model.result)
            DropdDown.init(itemView.context, result, array)
            buttonSearch.setOnClickListener {
                val job = result.text.toString()
                val uri = "https://www.jobstreet.co.id/id/job-search/$job-jobs/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(itemView.context, intent, null)
            }
        }
    }


}