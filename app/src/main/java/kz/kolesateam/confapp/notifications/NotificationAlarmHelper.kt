package kz.kolesateam.confapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar


const val NOTIFICATION_CONTENT_KEY = "notification_title"

class NotificationAlarmHelper(
        private val application: Application
) {
    fun createNotificationAlarm(
            content: String,
            eventDate: String
    ) {
        val alarmManager: AlarmManager? = application.getSystemService(
                Context.ALARM_SERVICE
        ) as AlarmManager

        val pendingIntent: PendingIntent = Intent(
                application,
                NotificationAlarmBroadcastReceiver::class.java
        ).apply {
            putExtra(NOTIFICATION_CONTENT_KEY, content)
        }.let {
            PendingIntent.getBroadcast(application, 0, it, 0)
        }

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(2020,11,16,13,56)

        alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
        )
    }
}

