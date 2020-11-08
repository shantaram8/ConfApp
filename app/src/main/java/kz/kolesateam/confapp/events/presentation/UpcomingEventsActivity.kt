package kz.kolesateam.confapp.events.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kz.kolesateam.confapp.R

class UpcomingEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)

        val asyncButton: Button = findViewById(R.id.activity_upcoming_events_async_button)
        val syncButton: Button = findViewById(R.id.activity_upcoming_events_sync_button)
        val jsonResult : TextView = findViewById(R.id.activity_upcoming_events_text_view)

        asyncButton.setOnClickListener {

        }
        syncButton.setOnClickListener {

        }

    }
}