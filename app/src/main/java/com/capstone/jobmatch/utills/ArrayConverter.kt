package com.capstone.jobmatch.utills

object ArrayConverter {

    fun convertStringToArray(string: String): Array<String> {
        val cleanedString = string.replace("[", "").replace("]", "")
        return cleanedString.split(",").map { it.trim() }.toTypedArray()
    }
}