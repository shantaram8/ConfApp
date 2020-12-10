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

private const val DATE_PLACE_FORMATTED_STRING = "%s - %s • %s"

class BranchAllEventsViewHolder(
    itemView: View,
    private val branchAllEventsClickListeners: UpcomingEventsClickListeners
) : BaseViewHolder<BranchAllEventsListItem>(itemView) {

    private val statusEventTextView: TextView = itemView.findViewById(R.id.status_event_text_view)
    private val eventCard: ConstraintLayout =
        itemView.findViewById(R.id.event_card_constraint_layout)


    private val eventDatePlace: TextView = itemView.findViewById(R.id.event_date_place_text_view)
    private val speakerName: TextView = itemView.findViewById(R.id.speaker_name_text_view)
    private val speakerJob: TextView = itemView.findViewById(R.id.speaker_job_text_view)
    private val eventTitle: TextView = itemView.findViewById(R.id.event_title_text_view)
    private val addToFavoritesIcon: ImageView = itemView.findViewById(R.id.to_favourite_image_view)


    override fun onBind(data: BranchAllEventsListItem) {

        val eventApiData: EventApiData = (data as? EventsListItem)?.data ?: return


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
            branchAllEventsClickListeners.onAddToFavoritesClick(eventApiData)
        }
        eventCard.setOnClickListener {
            branchAllEventsClickListeners.onEventClick(eventApiData)
        }
    }


}
