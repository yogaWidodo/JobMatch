package com.capstone.jobmatch.core.injection

import com.capstone.jobmatch.core.ui.main.MainViewModel
import com.capstone.jobmatch.core.api.ApiInterface
import com.capstone.jobmatch.core.model.Repository
import com.capstone.jobmatch.core.ui.forgot.ForgotPasswordViewModel
import com.capstone.jobmatch.core.ui.history.HistoryViewModel
import com.capstone.jobmatch.core.ui.joma.JomaViewModel
import com.capstone.jobmatch.core.ui.register.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        Retrofit.Builder()
            .baseUrl("https://jobmatch-4-eqdednyjrq-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }
    single {
        FirebaseAuth.getInstance()
    }
    single {
        FirebaseFirestore.getInstance()
    }
    single { Repository(get(), get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { JomaViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { MainViewModel(get()) }
}
