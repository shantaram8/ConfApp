package kz.kolesateam.confapp.events_details.presentation

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
import kz.kolesateam.confapp.favorite_events.domain.FavoriteEventsRepository
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.UpcomingEventsViewModel
import kz.kolesateam.confapp.upcoming_events.presentation.view.AddToFavoritesClickListener
import kz.kolesateam.confapp.upcoming_events.presentation.view.BackArrowClickListener
import kz.kolesateam.confapp.upcoming_events.presentation.view.FavoritesButtonClickListener
import kz.kolesateam.confapp.utils.extensions.getEventFormattedTime
import kz.kolesateam.confapp.utils.extensions.getParsedEventTime
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val EVENT_ID = "event_id"

class EventsDetailsActivity : AppCompatActivity(), AddToFavoritesClickListener, BackArrowClickListener, FavoritesButtonClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()
    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()
    private val favoriteEventsRepository: FavoriteEventsRepository by inject()

    private lateinit var speakerPhoto: ImageView
    private lateinit var speakerName: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventDescription: TextView
    private lateinit var speakerJob: TextView
    private lateinit var timePlace: TextView
    private lateinit var invitedSpeaker: TextView
    private lateinit var toBackArrow: ImageView
    private lateinit var toFavoritesImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_details)
        bindViews()
        val eventId: Int = intent.getIntExtra(EVENT_ID, 1)
        eventDetailsViewModel.getEventDetails(eventId)
        observeEventDetailsViewModel()
    }

    private fun setOnClickListeners(eventApiData: EventApiData) {
        toBackArrow.setOnClickListener {
            onBackArrowClick()
        }
        toFavoritesImageView.setOnClickListener {
            eventApiData.isFavorite = !eventApiData.isFavorite

            val toFavoriteImageResource = getFavoriteImageResource(eventApiData.isFavorite)
            toFavoritesImageView.setImageResource(toFavoriteImageResource)
            onAddToFavoritesClick(eventApiData)
        }
    }

    private fun observeEventDetailsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, {
            setData(it)
            toFavoritesImageView.setImageResource(getFavoriteImageResource(favoriteEventsRepository.isFavorite(it.id)))
            setOnClickListeners(it)
        })
    }

    private fun bindViews() {
        toBackArrow = findViewById(R.id.activity_events_details_back_arrow_image_view)
        toFavoritesImageView = findViewById(R.id.activity_events_details_to_favorites_image_view)
        speakerName = findViewById(R.id.activity_events_details_speaker_name_text_view)
        eventTitle = findViewById(R.id.activity_events_details_event_title_text_view)
        invitedSpeaker = findViewById(R.id.activity_events_details_speaker_invited_label_text_view)
        timePlace = findViewById(R.id.activity_events_details_time_place_text_view)
        eventDescription = findViewById(R.id.activity_events_details_event_desc_text_view)
        speakerJob = findViewById(R.id.activity_events_speaker_job_text_view)
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

    override fun onAddToFavoritesClick(eventData: EventApiData) {
        upcomingEventsViewModel.onAddToFavoriteClick(eventData)
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
private fun getFavoriteImageResource(
    isFavorite: Boolean
): Int = when (isFavorite) {
    true -> R.drawable.ic_favorite_fill
    else -> R.drawable.ic_favorite_border
}