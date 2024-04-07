package com.capstone.jobmatch.core.api

import com.capstone.jobmatch.core.model.PredictResponse
import com.capstone.jobmatch.core.model.PredictionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @POST("predict")
    fun predict(@Body request: PredictionRequest): Call<PredictResponse>

}
