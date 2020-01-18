package info.tommarsh.mynews.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tommarsh.mynews.core.article.data.local.model.Article
import info.tommarsh.mynews.core.article.data.local.model.Source
import info.tommarsh.mynews.core.article.data.local.source.ArticlesDao
import info.tommarsh.mynews.core.category.data.local.model.Category
import info.tommarsh.mynews.core.category.data.local.source.CategoryDao

@Database(entities = [Article::class, Source::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
internal abstract class
NewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun categoriesDao(): CategoryDao
}