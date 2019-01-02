package info.tommarsh.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.room.*
import info.tommarsh.data.model.local.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(vararg categories: Category)

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE selected = \'true\'")
    fun getSelectedCategories(): LiveData<List<Category>>

    @Update
    fun updateCategories(vararg categories: Category)
}