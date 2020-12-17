package kz.kolesateam.confapp.notifications

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events_details.presentation.EventsDetailsActivity

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
            content: String
    ) {
        val notification: Notification = getNotification(
                title = title,
                content = content
        )

        NotificationManagerCompat.from(application).notify(
                notificationCounter++,
                notification
        )
    }

    private fun getNotification(
            title: String,
            content: String
    ): Notification = NotificationCompat.Builder(
            application, CHANNEL_ID
    ).setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_favorite_fill)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getPendingIntent(content))
            .setAutoCancel(true)
            .build()

    private fun getPendingIntent(
            content: String
    ): PendingIntent {
        val detailsEventIntent = Intent(application.applicationContext, EventsDetailsActivity::class.java)
        return PendingIntent.getActivity(application.applicationContext, 0, detailsEventIntent, 0)
    }

    private fun initChannel() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                importance
        )
        val notificationManager: NotificationManager = application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}