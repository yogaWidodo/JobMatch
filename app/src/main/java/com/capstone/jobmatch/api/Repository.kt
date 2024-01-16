package com.capstone.jobmatch.api

import com.capstone.jobmatch.model.PredictResponse
import com.capstone.jobmatch.model.PredictionRequest
import retrofit2.Call

class Repository {
    fun predict(request: PredictionRequest): Call<PredictResponse> {
        return RetrofitInstance.getApiService().predict(request)

    }

}
