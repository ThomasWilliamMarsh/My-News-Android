package info.tommarsh.mynews.core.article.data.local.source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tommarsh.mynews.core.article.data.local.model.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT COUNT(*) FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun getOffsetForCategory(category: String): Int

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == :category")
    fun getArticlesForCategory(category: String): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun deleteCategory(category: String)

    @Query("DELETE FROM SOURCE_TABLE")
    suspend fun deleteSources(): Int
}