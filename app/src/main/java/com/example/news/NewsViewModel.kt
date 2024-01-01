package com.example.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.ArticlesItem
import com.example.news.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    var mutableLiveData: MutableLiveData<NewsResponse> = MutableLiveData()
    var articleMutableLiveData: MutableLiveData<MutableList<ArticlesItem>> = MutableLiveData()

    fun getNews(
        countryCode: String?,
        pageNumber: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveData.postValue(newsRepository.getNews(countryCode, pageNumber))
        }
    }

    fun insertArticle(article: ArticlesItem) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.insertArticle(article)
        }
    }

    fun getSavedArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            articleMutableLiveData.postValue(newsRepository.getSavedArticle())
        }
    }

    fun deleteArticle(articleUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteArticle(articleUrl)
        }
    }
}

