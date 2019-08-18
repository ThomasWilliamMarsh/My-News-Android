package info.tommarsh.mynews.repository.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_table")
data class Source(
    @PrimaryKey val id: String,
    val name: String
)