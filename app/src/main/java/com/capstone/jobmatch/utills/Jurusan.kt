package com.capstone.jobmatch.utills

object Jurusan {

    fun dataJurusan(str: String): String {
        val map = HashMap<String, String>()
        map["Science & Technology"] = "Sci&Tech"
        map["Communication & Management"] = "Comm&Mgmt"
        map["Others"] = "Others"
        return map[str].toString()
    }
}
