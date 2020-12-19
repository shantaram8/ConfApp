package kz.kolesateam.confapp.utils.extensions

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.ParseException

fun ZonedDateTime.getEventFormattedTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun getParsedEventTime(eventTime: String): ZonedDateTime = try {
    ZonedDateTime.parse(eventTime) ?: ZonedDateTime.now()
} catch (e: ParseException) {
    ZonedDateTime.now()
}