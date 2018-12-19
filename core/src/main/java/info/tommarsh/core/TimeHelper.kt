package info.tommarsh.core

import org.joda.time.DateTime
import org.joda.time.Minutes
import javax.inject.Inject

class TimeHelper
@Inject constructor() {

    companion object {
        private const val MINUTES_IN_DAY = 1440
        private const val MINUTES_IN_HOUR = 60
        private const val UNKNOWN = ""
    }

    fun timeBetween(now: DateTime = DateTime.now(), isoString: String): String {
        return try {
            val then = DateTime(isoString)
            val minutes = Minutes.minutesBetween(then, now)
            when {
                minutes.isGreaterThan(Minutes.minutes(MINUTES_IN_DAY)) -> "${minutes.toStandardDays().days} days ago"
                minutes.isGreaterThan(Minutes.minutes(MINUTES_IN_HOUR)) -> "${minutes.toStandardHours().hours} hours ago"
                else -> "${minutes.minutes} minutes ago"
            }
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        }
    }
}