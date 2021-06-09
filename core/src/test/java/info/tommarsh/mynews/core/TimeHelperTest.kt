package info.tommarsh.mynews.core

import info.tommarsh.mynews.core.util.TimeHelper
import info.tommarsh.mynews.test.UnitTest
import junit.framework.Assert.assertEquals
import org.joda.time.DateTime
import org.junit.Test

class TimeHelperTest : UnitTest<TimeHelper>() {

    private val timeHelper = TimeHelper()

    @Test
    fun `Get time string`() {
        val expected = "1 hour ago"

        val actual = timeHelper.timeBetween(
            now = DateTime.parse("2019-01-01T12:00:00+0000"),
            isoString = "2019-01-01T11:00:00+0000"
        )

        assertEquals(expected, actual)
    }

    override fun createSut(): TimeHelper {
        return TimeHelper()
    }
}