package info.tommarsh.mynews.repository.source.local.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tommarsh.mynews.repository.model.local.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg categories: Category)

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getCategories(): Flow<List<Category>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE selected = :selected")
    fun getSelectedCategoriesStream(selected: Boolean = true): Flow<List<Category>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE selected = :selected")
    suspend fun getSelectedCategories(selected: Boolean = true): List<Category>

    @Query("UPDATE CATEGORY_TABLE SET selected = :selected where id = :id")
    suspend fun updateCategory(id: String, selected: Boolean)
}