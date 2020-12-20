package kz.kolesateam.confapp.branch_all_events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.models.EventsListItem
import kz.kolesateam.confapp.upcoming_events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners
import kz.kolesateam.confapp.utils.extensions.getEventFormattedTime
import kz.kolesateam.confapp.utils.extensions.getParsedEventTime
import org.threeten.bp.ZonedDateTime

const val DATE_PLACE_FORMATTED_STRING = "%s - %s • %s"

class BranchAllEventsViewHolder(
    itemView: View,
    private val upcomingEventsClickListeners: UpcomingEventsClickListeners
) : BaseViewHolder<BranchAllEventsListItem>(itemView) {

    private val statusEventTextView: TextView = itemView.findViewById(R.id.status_event_text_view)
    private val eventCard: ConstraintLayout =
        itemView.findViewById(R.id.event_card_constraint_layout)


    private val eventDatePlace: TextView = itemView.findViewById(R.id.event_date_place_text_view)
    private val speakerName: TextView = itemView.findViewById(R.id.speaker_name_text_view)
    private val speakerJob: TextView = itemView.findViewById(R.id.speaker_job_text_view)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_title_text_view)
    private val addToFavorites: ImageView = itemView.findViewById(R.id.to_favourite_image_view)


    override fun onBind(data: BranchAllEventsListItem) {

        val eventApiData: EventApiData = (data as? EventsListItem)?.data ?: return

        val startTime = getParsedEventTime(eventApiData.startTime)
        val endTime = getParsedEventTime(eventApiData.endTime)
        val currentTime = ZonedDateTime.now()

        if (currentTime.isAfter(endTime)) {
            statusEventTextView.visibility = View.VISIBLE
            eventCard.setBackgroundResource(R.drawable.bg_event_card_completed_item)
        }

        val eventDatePlaceText = DATE_PLACE_FORMATTED_STRING.format(
            startTime.getEventFormattedTime(),
            endTime.getEventFormattedTime(),
            eventApiData.place
        )

        eventDatePlace.text = eventDatePlaceText
        speakerName.text = eventApiData.speaker?.fullName
        speakerJob.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title



        addToFavorites.setImageResource(
            getFavoriteImageResource(eventApiData.isFavorite)
        )

        addToFavorites.setOnClickListener {
            eventApiData.isFavorite = !eventApiData.isFavorite

            addToFavorites.setImageResource(
                getFavoriteImageResource(eventApiData.isFavorite)
            )

            upcomingEventsClickListeners.onAddToFavoritesClick(eventApiData)
        }
        eventCard.setOnClickListener {
            upcomingEventsClickListeners.onEventClick(eventApiData)
        }
    }

    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_fill
        else -> R.drawable.ic_favorite_border
    }


}
