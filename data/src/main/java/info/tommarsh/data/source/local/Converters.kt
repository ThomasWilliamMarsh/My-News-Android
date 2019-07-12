package info.tommarsh.data.source.local

import androidx.room.TypeConverter
import info.tommarsh.data.model.local.Source

internal class Converters {

    companion object {
        private const val NUM_FIELDS = 2
        private const val COLUMN_ID = 0
        private const val COLUMN_NAME = 1
    }

    @TypeConverter
    fun stringToSource(value: String): Source {
        val split = value.split(" ")
        return if (split.size == NUM_FIELDS) {
            Source(split[COLUMN_ID], split[COLUMN_NAME])
        } else Source(" ", " ")
    }

    @TypeConverter
    fun sourceToString(source: Source) = "${source.id} ${source.name}"
}