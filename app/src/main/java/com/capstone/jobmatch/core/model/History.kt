package com.capstone.jobmatch.core.model

data class History(
    val jurusan: String,
    val pengalaman: String,
    val skills: String,
    val result: String,
    val date: String
) {
    constructor() : this("", "", "", "", "")
}

