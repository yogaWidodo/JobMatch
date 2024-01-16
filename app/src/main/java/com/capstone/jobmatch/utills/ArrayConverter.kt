package com.capstone.jobmatch.utills

object ArrayConverter {

    fun convertStringToArray(string: String): Array<String> {
        val cleanedString = string.replace("[", "").replace("]", "")
        return cleanedString.split(",").map { it.trim() }.toTypedArray()
    }

    //delete [] from string
    fun removeBrackets(str: String): String {
        return str.removeSurrounding("[", "]")
    }




    //buat fungsi untuk memotong kalimat menjadi satu kata saja
    fun cutOffEnd(text: String): String {
        return text.split(" ")[0]
    }


}