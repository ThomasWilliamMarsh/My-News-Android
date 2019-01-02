package info.tommarsh.data.source.local.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tommarsh.data.model.local.Article

@Dao
interface ArticlesDao {

    @Transaction
    fun replaceBreakingArticles(articles: List<Article>) {
        deleteArticles()
        insertBreakingArticles(*articles.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreakingArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE")
    fun getBreakingArticles(): LiveData<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE")
    fun deleteArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    fun deleteSources(): Int
}