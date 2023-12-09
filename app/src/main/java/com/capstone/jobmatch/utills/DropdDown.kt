package com.capstone.jobmatch.utills

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.capstone.jobmatch.R
import java.util.Arrays

object DropdDown {
    fun init(
        context: Context,
        autoCompleteTextView: AutoCompleteTextView,
        array: Array<out String>
    ) {
        dropDown(context, autoCompleteTextView, array)
    }

    private fun dropDown(
        context: Context,
        autoCompleteTextView: AutoCompleteTextView,
        arrays: Array<out String>
    ) {
        val adapter = ArrayAdapter(context, R.layout.dropdown_item, arrays)
        autoCompleteTextView.setAdapter(adapter)
    }
}