package info.tommarsh.mynews.core.article.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class Source(
    @PrimaryKey val id: String,
    val name: String
)