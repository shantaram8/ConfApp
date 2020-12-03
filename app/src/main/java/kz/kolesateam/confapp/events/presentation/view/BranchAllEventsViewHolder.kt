package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchAllEventsViewHolder(
    itemView: View,
    private val branchAllEventsClickListeners: UpcomingEventsClickListeners
) : BaseViewHolder<EventApiData>(itemView) {

    private val cardView: CardView = itemView.findViewById(R.id.card_view)


    private val eventDatePlace: TextView = itemView.findViewById(R.id.event_date_place_text_view)
    private val speakerName: TextView = itemView.findViewById(R.id.speaker_name_text_view)
    private val speakerJob: TextView = itemView.findViewById(R.id.speaker_job_text_view)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_title_text_view)
    private val addToFavoritesIcon: ImageView = itemView.findViewById(R.id.to_favourite_image_view)


    override fun onBind(data: EventApiData) {

        val eventDatePlaceText = "%s - %s • %s".format(
            data.startTime,
            data.endTime,
            data.place
        )

        eventDatePlace.text = eventDatePlaceText
        speakerName.text = data.speaker?.fullName
        speakerJob.text = data.speaker?.job
        eventTitle.text = data.title

        addToFavoritesIcon.setOnClickListener {
            branchAllEventsClickListeners.onAddToFavoritesClick(data)
        }
        cardView.setOnClickListener{
            branchAllEventsClickListeners.onEventClick(data)
        }
    }


}
