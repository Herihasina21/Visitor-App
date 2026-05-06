package com.example.visitor.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // API base url
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: VisitorApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VisitorApi::class.java)
    }
}