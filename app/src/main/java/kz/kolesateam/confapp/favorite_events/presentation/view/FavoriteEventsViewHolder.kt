package kz.kolesateam.confapp.favorite_events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.branch_all_events.presentation.view.DATE_PLACE_FORMATTED_STRING
import kz.kolesateam.confapp.models.EventApiData
import kz.kolesateam.confapp.upcoming_events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners

class FavoriteEventsViewHolder(
        itemView: View,
        private val favoriteClickListeners: UpcomingEventsClickListeners
): BaseViewHolder<EventApiData>(itemView) {
    private val eventCard: ConstraintLayout =
            itemView.findViewById(R.id.event_card_constraint_layout)


    private val eventDatePlace: TextView = itemView.findViewById(R.id.event_date_place_text_view)
    private val speakerName: TextView = itemView.findViewById(R.id.speaker_name_text_view)
    private val speakerJob: TextView = itemView.findViewById(R.id.speaker_job_text_view)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_title_text_view)
    private val addToFavoritesIcon: ImageView = itemView.findViewById(R.id.to_favourite_image_view)


    override fun onBind(data: EventApiData) {

        val eventApiData: EventApiData = data


        val eventDatePlaceText = DATE_PLACE_FORMATTED_STRING.format(
                eventApiData.startTime,
                eventApiData.endTime,
                eventApiData.place
        )

        eventDatePlace.text = eventDatePlaceText
        speakerName.text = eventApiData.speaker?.fullName
        speakerJob.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title

        addToFavoritesIcon.setOnClickListener {
            favoriteClickListeners.onAddToFavoritesClick(eventApiData)
        }
        eventCard.setOnClickListener {
            favoriteClickListeners.onEventClick()
        }
    }


}