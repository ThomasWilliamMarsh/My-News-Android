package info.tommarsh.mynews.core.preferences

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Test

class SharedPreferencesRepositoryTest {

    private val preferences = mock<SharedPreferences>()

    private val repository = SharedPreferencesRepository(preferences)

    @Test
    fun `Outputs comma separated countries`() {
        val expected = "uk, us"

        whenever(preferences.getStringSet(any(), any()))
            .thenReturn(setOf("uk", "us"))

        val actual = repository.getCountry()

        assertEquals(expected, actual)
    }
}