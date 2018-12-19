package info.tommarsh.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tommarsh.data.model.local.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreakingArticles(vararg articles: Article)

    @Query("SELECT * FROM ARTICLE_TABLE")
    fun getBreakingArticles(): LiveData<List<Article>>

    @Query("DELETE FROM ARTICLE_TABLE")
    fun deleteArticles(): Int

    @Query("DELETE FROM SOURCE_TABLE")
    fun deleteSources(): Int
}