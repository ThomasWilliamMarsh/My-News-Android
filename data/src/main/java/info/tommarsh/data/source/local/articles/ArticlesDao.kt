package info.tommarsh.data.source.local.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tommarsh.data.model.local.Article

@Dao
interface ArticlesDao {

    @Transaction
    suspend fun replaceBreakingArticles(articles: List<Article>) {
        deleteBreakingArticles()
        insertArticles(*articles.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == 'top-news'")
    fun getBreakingArticles(): LiveData<List<Article>>

    @Transaction
    suspend fun replaceCategories(category: String, articles: List<Article>) {
        deleteCategory(category)
        insertArticles(*articles.toTypedArray())
    }

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category != 'top-news'")
    fun getFeed(): LiveData<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun deleteCategory(category: String)

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == 'top-news'")
    suspend fun deleteBreakingArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    suspend fun deleteSources(): Int
}