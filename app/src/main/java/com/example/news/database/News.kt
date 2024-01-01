package com.example.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.model.ArticlesItem

@Database(
    entities = [ArticlesItem::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class News : RoomDatabase() {

    abstract fun NewsDao(): NewsDao

    companion object {

        @Volatile
        private var INSTANCE: News? = null

        fun getDatabase(context: Context): News {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): News {
            return Room.databaseBuilder(
                context.applicationContext,
                News::class.java,
                "news_database.db"
            )
                .build()
        }
    }

}