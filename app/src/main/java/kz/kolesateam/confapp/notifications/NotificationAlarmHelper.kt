package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.util.Log
import kz.kolesateam.confapp.upcoming_events.presentation.EVENT_ID
import kz.kolesateam.confapp.utils.extensions.getParsedEventTime
import org.threeten.bp.ZonedDateTime


const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
    private val application: Application
) {
    fun createNotificationAlarm(
        content: String,
        eventDate: String,
        eventId: Int
    ) {
        val alarmManager: AlarmManager = application.getSystemService(
            Context.ALARM_SERVICE
        ) as AlarmManager

        val pendingIntent: PendingIntent = Intent(
            application,
            NotificationAlarmBroadcastReceiver::class.java
        ).apply {
            putExtra(NOTIFICATION_CONTENT_KEY, content)
            putExtra(EVENT_ID, eventId)
        }.let {
            PendingIntent.getBroadcast(application, 0, it, FLAG_ONE_SHOT)
        }

        val eventZonedDateTime: ZonedDateTime = getParsedEventTime(eventDate)
        val currentZonedDateTime: ZonedDateTime = ZonedDateTime.now()
        val timeInMillis: Long = ((eventZonedDateTime.toEpochSecond() - 300) * 1000)

        if (currentZonedDateTime.isBefore(eventZonedDateTime)) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }
}

