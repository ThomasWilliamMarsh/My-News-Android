package info.tommarsh.data.source.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tommarsh.data.model.local.Article
import info.tommarsh.data.model.local.Category
import info.tommarsh.data.model.local.Source
import info.tommarsh.data.source.local.articles.ArticlesDao
import info.tommarsh.data.source.local.category.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Article::class, Source::class, Category::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
    abstract fun categoriesDao(): CategoryDao

    override fun init(configuration: DatabaseConfiguration) {
        super.init(configuration)
        GlobalScope.launch(Dispatchers.IO) {
            categoriesDao().insertCategories(
                Category("business", "Business", false),
                Category("entertainment", "Entertainment", false),
                Category("general", "General", false),
                Category("health", "Health", false),
                Category("science", "Science", false),
                Category("sports", "Sports", false),
                Category("technology", "Technology", false)
            )
        }
    }
}