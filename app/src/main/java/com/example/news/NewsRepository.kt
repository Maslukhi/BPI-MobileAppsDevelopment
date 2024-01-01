package com.example.news

import com.example.news.api.ApiConfig
import com.example.news.database.News
import com.example.news.model.ArticlesItem
import com.example.news.model.NewsResponse

class NewsRepository(private val database: News) {

    suspend fun getNews(countryCode: String?, pageNumber: Int): NewsResponse? {
        val response =
            ApiConfig.api.getNews(
                countryCode, pageNumber,
                BuildConfig.API_KEY
            )
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    fun insertArticle(article: ArticlesItem) {
        database.NewsDao().insert(article)
    }

    fun getSavedArticle(): MutableList<ArticlesItem> {
        return database.NewsDao().getArticles()
    }

    fun deleteArticle(articleUrl: String) {
        database.NewsDao().deleteArticle(articleUrl)
    }
}