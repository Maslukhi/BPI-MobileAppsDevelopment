package com.example.news.api


import com.example.news.BuildConfig
import com.example.news.model.NewsResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String?,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsResponse>


    @GET("v2/everything")
    suspend fun detailNews(
        @Query("q") keyword: String? = "apple",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String?,
        @Query("pageSize") pageSize: String = "50"
    ): Response<NewsResponse>

}