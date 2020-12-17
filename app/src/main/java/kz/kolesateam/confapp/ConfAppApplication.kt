package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.di.*
import kz.kolesateam.confapp.notifications.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication.Companion.init
import org.koin.core.context.startKoin

class ConfAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.init(this)

        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                    applicationModule,
                    eventScreenModule,
                    userNameModule,
                    favoriteEventsModule,
                    eventDetailsModule
            )
        }
    }
}