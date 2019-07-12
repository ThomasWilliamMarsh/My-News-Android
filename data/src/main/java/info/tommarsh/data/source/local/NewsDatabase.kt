package info.tommarsh.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tommarsh.data.model.local.Article
import info.tommarsh.data.model.local.Category
import info.tommarsh.data.model.local.Source
import info.tommarsh.data.source.local.articles.ArticlesDao
import info.tommarsh.data.source.local.category.CategoryDao

@Database(entities = [Article::class, Source::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class NewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun categoriesDao(): CategoryDao
}