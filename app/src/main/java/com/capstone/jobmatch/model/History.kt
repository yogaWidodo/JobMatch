package com.capstone.jobmatch.model

//@Parcelize
//@Entity(tableName = "history")
//data class History(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int,
//    val jurusan: String,
//    val tahunKerja: String,
//    val skills: String,
//    val result: String,
//    val date: String
//) : Parcelable

class History(
    val jurusan: String,
    val pengalaman: String,
    val skills: String,
    val result: String,
    val date: String
) {
    constructor() : this("", "", "", "", "")
}

