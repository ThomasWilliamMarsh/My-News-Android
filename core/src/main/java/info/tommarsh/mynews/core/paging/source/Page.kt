package info.tommarsh.mynews.core.paging.source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paging_table")
data class Page(
    @PrimaryKey val id: String,
    val page: Int
)