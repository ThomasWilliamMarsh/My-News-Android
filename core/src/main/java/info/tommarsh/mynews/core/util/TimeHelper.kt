package info.tommarsh.mynews.core.util

import org.joda.time.DateTime
import org.joda.time.Minutes
import javax.inject.Inject

class TimeHelper
@Inject internal constructor() {

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
                minutes.isGreaterThan(Minutes.minutes(MINUTES_IN_DAY - 1)) -> {
                    val days = minutes.toStandardDays().days
                    "$days ${if (days > 1) "days" else "day"} ago"
                }
                minutes.isGreaterThan(Minutes.minutes(MINUTES_IN_HOUR - 1)) -> {
                    val hours = minutes.toStandardHours().hours
                    "$hours ${if (hours > 1) "hours" else "hour"} ago"
                }
                else -> "${minutes.minutes} minutes ago"
            }
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        }
    }
}