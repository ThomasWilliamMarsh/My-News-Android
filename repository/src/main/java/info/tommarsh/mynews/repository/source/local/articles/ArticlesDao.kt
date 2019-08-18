package info.tommarsh.mynews.repository.source.local.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tommarsh.mynews.repository.model.local.Article

@Dao
interface ArticlesDao {

    @Transaction
    suspend fun replaceBreakingArticles(articles: List<Article>) {
        deleteBreakingArticles()
        insertArticles(*articles.toTypedArray())
    }

    @Transaction
    suspend fun replaceCategories(category: String, articles: List<Article>) {
        deleteCategory(category)
        insertArticles(*articles.toTypedArray())
    }

    @Query("DELETE FROM ARTICLE_TABLE WHERE category IN (SELECT id FROM CATEGORY_TABLE WHERE selected = 0)")
    suspend fun deleteUnselectedCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == 'top-news'")
    fun getBreakingArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category != 'top-news'")
    fun getFeed(): LiveData<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun deleteCategory(category: String)

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == 'top-news'")
    suspend fun deleteBreakingArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    suspend fun deleteSources(): Int
}