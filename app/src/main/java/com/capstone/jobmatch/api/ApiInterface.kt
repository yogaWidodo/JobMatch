package com.capstone.jobmatch.api

import com.capstone.jobmatch.model.PredictResponse
import com.capstone.jobmatch.model.PredictionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("predict")
    fun predict(@Body request: PredictionRequest): Call<PredictResponse>

}
