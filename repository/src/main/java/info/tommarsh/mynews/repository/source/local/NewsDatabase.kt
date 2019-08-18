package info.tommarsh.mynews.repository.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tommarsh.mynews.repository.model.local.Article
import info.tommarsh.mynews.repository.model.local.Category
import info.tommarsh.mynews.repository.model.local.Source
import info.tommarsh.mynews.repository.source.local.articles.ArticlesDao
import info.tommarsh.mynews.repository.source.local.category.CategoryDao

@Database(entities = [Article::class, Source::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class NewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun categoriesDao(): CategoryDao
}