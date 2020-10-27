package info.tommarsh.mynews.core.paging.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PagingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setPageForCategory(vararg page: Page)

    @Query("SELECT page FROM PAGING_TABLE WHERE id == :id")
    suspend fun getPageForCategory(id: String): Int
}