package com.example.news.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig() {


    companion object {

        private val apiconfig by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            apiconfig.create(ApiService::class.java)
        }

    }
}