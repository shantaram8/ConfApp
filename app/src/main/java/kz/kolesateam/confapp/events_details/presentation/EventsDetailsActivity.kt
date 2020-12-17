package kz.kolesateam.confapp.events_details.presentation

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.EventApiData
import org.koin.android.ext.android.inject

class EventsDetailsActivity : AppCompatActivity() {

    private val eventDetailsViewModel: EventDetailsViewModel by inject()
//
//    private val speakerPhoto: ImageView = findViewById(R.id.activity_events_details_speaker_photo_image_view)
    private lateinit var speakerName: TextView
//    private val speakerJob: TextView = findViewById(R.id.speaker_job_text_view)
//    private val eventTitle: TextView = findViewById(R.id.event_title_text_view)
//    private val addToFavoritesIcon: ImageView = findViewById(R.id.to_favourite_image_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_details)
        bindViews()
        eventDetailsViewModel.onStart(2)
        observeEventDetailsViewModel()
    }
    private fun bindViews() {
        speakerName = findViewById(R.id.activity_events_details_speaker_name_text_view)
    }
    private fun observeEventDetailsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, {
            setData(it)
        })
    }
    private fun setData(eventApiData: EventApiData) {
        speakerName.text = eventApiData.speaker?.fullName
    }
}