package kz.kolesateam.confapp.notifications

import android.app.*
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity
import kz.kolesateam.confapp.upcoming_events.presentation.EVENT_ID

private const val CHANNEL_ID = "favorite_notification_channel"
private const val CHANNEL_NAME = "favorite_notification_channel_name"

object NotificationHelper {

    private lateinit var application: Application
    private var notificationCounter: Int = 0

    fun init(application: Application) {
        this.application = application
        initChannel()
    }

    fun sendNotification(
        title: String,
        content: String,
        eventId: Int
    ) {
        val notification: Notification = getNotification(
            title = title,
            content = content,
            eventId = eventId
        )

        NotificationManagerCompat.from(application).notify(
            notificationCounter++,
            notification
        )
    }

    private fun getNotification(
        title: String,
        content: String,
        eventId: Int
    ): Notification = NotificationCompat.Builder(
        application, CHANNEL_ID
    ).setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(R.drawable.ic_favorite_fill)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(getPendingIntent(content, eventId))
        .setAutoCancel(true)
        .build()

    private fun getPendingIntent(
        content: String,
        eventId: Int
    ): PendingIntent {
        val detailsEventIntent =
            Intent(application.applicationContext, EventsDetailsActivity::class.java)
        detailsEventIntent.putExtra(EVENT_ID, eventId)
        return PendingIntent.getActivity(
            application.applicationContext,
            0,
            detailsEventIntent,
            FLAG_ONE_SHOT,
        )
    }

    private fun initChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            importance
        )
        val notificationManager: NotificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}