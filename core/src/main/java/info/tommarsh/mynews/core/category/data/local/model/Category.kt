package info.tommarsh.mynews.core.category.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String,
    val selected: Boolean
)