package com.example.news.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.model.ArticlesItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: ArticlesItem): Long

    @Query("SELECT * FROM articles")
    fun getArticles(): MutableList<ArticlesItem>

    @Query("DELETE FROM articles WHERE url = :articleUrl")
    fun deleteArticle(articleUrl: String)
}