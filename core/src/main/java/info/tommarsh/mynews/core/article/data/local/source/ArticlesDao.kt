package info.tommarsh.mynews.core.article.data.local.source

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tommarsh.mynews.core.article.data.local.model.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE WHERE category == :category")
    fun getArticlesForCategory(category: String): PagingSource<Int, Article>

    @Query("DELETE FROM ARTICLE_TABLE WHERE category == :category")
    suspend fun deleteCategory(category: String)
}