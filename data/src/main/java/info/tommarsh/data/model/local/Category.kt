package info.tommarsh.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String,
    val selected: Boolean
)