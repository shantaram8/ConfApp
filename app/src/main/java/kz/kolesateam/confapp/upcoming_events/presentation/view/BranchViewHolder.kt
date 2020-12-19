package kz.kolesateam.confapp.upcoming_events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchApiData
import kz.kolesateam.confapp.models.BranchListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.UpcomingEventListItem
import kz.kolesateam.confapp.utils.extensions.getEventFormattedTime
import kz.kolesateam.confapp.utils.extensions.getParsedEventTime

class BranchViewHolder(
        itemView: View,
        private val upcomingEventsClickListeners: UpcomingEventsClickListeners
) : BaseViewHolder<UpcomingEventListItem>(itemView) {


    private val currentBranchEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val nextBranchEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchLinearLayout: LinearLayout = itemView.findViewById(R.id.branch_linear_layout)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)

    private val toFavouritesImageViewCurrent: ImageView = itemView.findViewById(R.id.to_favourite_image_view)
    private val toFavouritesImageViewNext: ImageView = nextBranchEvent.findViewById(R.id.to_favourite_image_view)

    private val currentEventDatePlace: TextView =
            currentBranchEvent.findViewById(R.id.event_date_place_text_view)
    private val currentSpeakerName: TextView =
            currentBranchEvent.findViewById(R.id.speaker_name_text_view)
    private val currentSpeakerJob: TextView =
            currentBranchEvent.findViewById(R.id.speaker_job_text_view)
    private val currentEventTitle: TextView =
            currentBranchEvent.findViewById(R.id.event_title_text_view)

    private val nextEventDatePlace: TextView =
            nextBranchEvent.findViewById(R.id.event_date_place_text_view)
    private val nextSpeakerName: TextView =
            nextBranchEvent.findViewById(R.id.speaker_name_text_view)
    private val nextSpeakerJob: TextView = nextBranchEvent.findViewById(R.id.speaker_job_text_view)
    private val nextEventTitle: TextView = nextBranchEvent.findViewById(R.id.event_title_text_view)


    init {
        nextBranchEvent.setBackgroundResource(R.drawable.bg_event_card_completed_item)
        nextBranchEvent.findViewById<TextView>(R.id.next_event_text_view).visibility = View.VISIBLE


    }


    override fun onBind(data: UpcomingEventListItem) {

        val branchApiData: BranchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

        val startTime = getParsedEventTime(currentEvent.startTime).getEventFormattedTime()
        val endTime = getParsedEventTime(currentEvent.endTime).getEventFormattedTime()
        val startTimeNextEvent = getParsedEventTime(nextEvent.startTime).getEventFormattedTime()
        val endTimeNextEvent = getParsedEventTime(nextEvent.endTime).getEventFormattedTime()

        val currentEventDatePlaceText = "%s - %s • %s".format(
                startTime,
                endTime,
                currentEvent.place
        )

        currentEventDatePlace.text = currentEventDatePlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName
        currentSpeakerJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        val nextEventDatePlaceText = "%s - %s • %s".format(
                startTimeNextEvent,
                endTimeNextEvent,
                nextEvent.place
        )

        nextEventDatePlace.text = nextEventDatePlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName
        nextSpeakerJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title

        val favoriteImageResourceCurrent = getFavoriteImageResource(currentEvent.isFavorite)
        toFavouritesImageViewCurrent.setImageResource(favoriteImageResourceCurrent)
        val favoriteImageResourceNext = getFavoriteImageResource(nextEvent.isFavorite)
        toFavouritesImageViewNext.setImageResource(favoriteImageResourceNext)

        branchLinearLayout.setOnClickListener {
            upcomingEventsClickListeners.onBranchClick(data)
        }
        currentBranchEvent.setOnClickListener {
            upcomingEventsClickListeners.onEventClick(currentEvent)
        }
        toFavouritesImageViewCurrent.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite

            val toFavoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            toFavouritesImageViewCurrent.setImageResource(toFavoriteImageResource)

            upcomingEventsClickListeners.onAddToFavoritesClick(currentEvent)

        }
        toFavouritesImageViewNext.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite

            val toFavoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            toFavouritesImageViewNext.setImageResource(toFavoriteImageResource)

            upcomingEventsClickListeners.onAddToFavoritesClick(nextEvent)
        }

    }

    private fun getFavoriteImageResource(
            isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_fill
        else -> R.drawable.ic_favorite_border
    }


}
