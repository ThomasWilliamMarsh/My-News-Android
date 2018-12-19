package info.tommarsh.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.tommarsh.data.model.local.Article
import info.tommarsh.data.model.local.Source

@Database(entities = [Article::class, Source::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}