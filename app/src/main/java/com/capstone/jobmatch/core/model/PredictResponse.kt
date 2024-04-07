package com.capstone.jobmatch.core.model

data class PredictResponse(
    val label: ArrayList<String>
)

data class PredictionRequest(
    val degree: String,
    val job: Int,
    val key: String,
)

