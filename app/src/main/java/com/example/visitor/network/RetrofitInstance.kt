package com.example.visitor.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // API base url
    private const val BASE_URL = "https://visitor-management-production-a75e.up.railway.app/"

    val api: VisitorApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VisitorApi::class.java)
    }
}