package kz.kolesateam.confapp.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.upcoming_events.presentation.EVENT_ID

class NotificationAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val content: String = intent?.getStringExtra(NOTIFICATION_CONTENT_KEY).orEmpty()
        val eventId = intent?.getIntExtra(EVENT_ID, 0)

        NotificationHelper.sendNotification(
            title = "Не пропустите следующий доклад",
            content = content,
            eventId = eventId ?: 0
        )
    }
}