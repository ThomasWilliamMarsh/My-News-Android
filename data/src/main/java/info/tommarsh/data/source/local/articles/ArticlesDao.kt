package info.tommarsh.data.source.local.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tommarsh.data.model.local.Article

@Dao
interface ArticlesDao {

    @Transaction
    fun replaceBreakingArticles(articles: List<Article>) {
        deleteBreakingArticles()
        insertArticles(*articles.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == 'top-news'")
    fun getBreakingArticles(): LiveData<List<Article>>

    @Transaction
    fun replaceFeed(articles: List<Article>) {
        deleteFeed()
        insertArticles(*articles.toTypedArray())
    }

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category != 'top-news'")
    fun getFeed(): LiveData<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category != 'top-news'")
    fun deleteFeed()

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == 'top-news'")
    fun deleteBreakingArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    fun deleteSources(): Int
}