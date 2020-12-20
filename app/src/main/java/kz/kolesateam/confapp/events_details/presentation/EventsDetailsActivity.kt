package kz.kolesateam.confapp.events_details.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branch_all_events.presentation.view.DATE_PLACE_FORMATTED_STRING
import kz.kolesateam.confapp.favorite_events.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.BackArrowClickListener
import kz.kolesateam.confapp.upcoming_events.presentation.view.FavoritesButtonClickListener
import kz.kolesateam.confapp.utils.extensions.getEventFormattedTime
import kz.kolesateam.confapp.utils.extensions.getParsedEventTime
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val EVENT_ID = "event_id"

class EventsDetailsActivity : AppCompatActivity(), BackArrowClickListener, FavoritesButtonClickListener {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()

    private lateinit var speakerPhoto: ImageView
    private lateinit var speakerName: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventDescription: TextView
    private lateinit var speakerJob: TextView
    private lateinit var timePlace: TextView
    private lateinit var invitedSpeaker: TextView
    private lateinit var addToFavoritesIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_details)
        bindViews()

        val eventId: Int = intent.getIntExtra(EVENT_ID, 1)
        eventDetailsViewModel.getEventDetails(eventId)
        observeEventDetailsViewModel()
    }

    private fun observeEventDetailsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, {
            setData(it)
        })
    }

    private fun bindViews() {
        speakerName = findViewById(R.id.activity_events_details_speaker_name_text_view)
        eventTitle = findViewById(R.id.activity_events_details_event_title_text_view)
        invitedSpeaker = findViewById(R.id.activity_events_details_speaker_invited_label_text_view)
        timePlace = findViewById(R.id.activity_events_details_time_place_text_view)
        eventDescription = findViewById(R.id.activity_events_details_event_desc_text_view)
        speakerJob = findViewById(R.id.activity_events_speaker_job_text_view)
        addToFavoritesIcon = findViewById(R.id.activity_events_favorite_image_view)
        speakerPhoto = findViewById(R.id.activity_events_details_speaker_photo_image_view)
    }

    private fun setData(eventApiData: EventApiData) {

        eventDescription.text = eventApiData.description
        speakerJob.text = eventApiData.speaker?.job
        speakerName.text = eventApiData.speaker?.fullName
        eventTitle.text = eventApiData.title

        setImageFromUrl(speakerPhoto, eventApiData)

        if (eventApiData.speaker?.isInvited == true) {
            invitedSpeaker.visibility = View.VISIBLE
        } else {
            invitedSpeaker.visibility = View.GONE
        }

        val startTime = getParsedEventTime(eventApiData.startTime).getEventFormattedTime()
        val endTime = getParsedEventTime(eventApiData.endTime).getEventFormattedTime()

        timePlace.text = DATE_PLACE_FORMATTED_STRING.format(
            startTime,
            endTime,
            eventApiData.place
        )

    }

    override fun onFavoritesButtonClick() = Unit

    override fun onBackArrowClick() {
        onBackPressed()
    }
}

private fun setImageFromUrl(imageView: ImageView, eventApiData: EventApiData) {
    Glide.with(imageView)
        .load(eventApiData.speaker?.photoUrl)
        .apply(
            RequestOptions()
                .override(Target.SIZE_ORIGINAL)
                .format(DecodeFormat.PREFER_ARGB_8888)
        )
        .into(imageView)
}