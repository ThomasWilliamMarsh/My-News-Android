package info.tommarsh.data.source.local.category

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import info.tommarsh.data.model.local.Category


class PrePopulater : RoomDatabase.Callback() {

    companion object {
        val topNews = Category("top-news", "Top News", false)
        val business = Category("business", "Business", false)
        val entertainment = Category("entertainment", "Entertainment", false)
        val general = Category("general", "General", false)
        val health = Category("health", "Health", false)
        val science = Category("science", "Science", false)
        val sports = Category("sports", "Sports", false)
        val technology = Category("technology", "Technology", false)

    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        listOf(
            business,
            entertainment,
            general,
            health,
            science,
            sports,
            technology
        ).forEach { db.insert("category_table", SQLiteDatabase.CONFLICT_REPLACE, createCategory(it)) }
    }

    private fun createCategory(category: Category): ContentValues {
        val value = ContentValues()
        value.put("name", category.name)
        value.put("id", category.id)
        value.put("selected", category.selected)
        return value
    }
}