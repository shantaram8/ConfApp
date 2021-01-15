package kz.kolesateam.confapp.empty_favorites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsActivity

class EmptyFavoritesActivity : AppCompatActivity() {

    private lateinit var toUpcomingEventsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_favorites)

        toUpcomingEventsButton = findViewById(R.id.activity_empty_favorites_to_upcoming_events_button)

        toUpcomingEventsButton.setOnClickListener {
            startActivity(Intent(this, UpcomingEventsActivity::class.java))
        }
    }
}