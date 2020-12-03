package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchAllEventsViewHolder(
    itemView: View,
) : BaseViewHolder<EventApiData>(itemView) {

    private val currentBranchEvent: View = itemView

    private val currentEventDatePlace: TextView =
        currentBranchEvent.findViewById(R.id.event_date_place_text_view)
    private val currentSpeakerName: TextView =
        currentBranchEvent.findViewById(R.id.speaker_name_text_view)
    private val currentSpeakerJob: TextView =
        currentBranchEvent.findViewById(R.id.speaker_job_text_view)
    private val currentEventTitle: TextView =
        currentBranchEvent.findViewById(R.id.event_title_text_view)



    override fun onBind(
        data: EventApiData
    ) {

        val currentEventDatePlaceText = "%s - %s • %s".format(
            data.startTime,
            data.endTime,
            data.place
        )

        currentEventDatePlace.text = currentEventDatePlaceText
        currentSpeakerName.text = data.speaker?.fullName
        currentSpeakerJob.text = data.speaker?.job
        currentEventTitle.text = data.title

    }

}
