package kz.kolesateam.confapp.events.presentation.view

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.DefaultBranchAllEventsRepository
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.BranchListItem
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem
import kz.kolesateam.confapp.events.domain.BranchAllEventsRepository
import kz.kolesateam.confapp.events.presentation.BranchAllEventsActivity
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity
import kz.kolesateam.confapp.models.ProgressState
import kz.kolesateam.confapp.models.ResponseData

class BranchViewHolder(
    itemView: View,
) : BaseViewHolder<UpcomingEventListItem>(itemView) {

    private val currentBranchEvent: View = itemView.findViewById(R.id.branch_current_event)
    private val nextBranchEvent: View = itemView.findViewById(R.id.branch_next_event)

    private val branchLinearLayout: LinearLayout = itemView.findViewById(R.id.branch_linear_layout)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)

    private val toFavouritesImageView: ImageView =
        itemView.findViewById(R.id.to_favourite_image_view)

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
        currentBranchEvent.findViewById<TextView>(R.id.next_event_text_view).visibility =
            View.INVISIBLE
    }

    override fun onBind(
        data: UpcomingEventListItem
    ) {

        val branchApiData: BranchApiData = (data as? BranchListItem)?.data ?: return

        branchTitle.text = branchApiData.title
        val currentEvent: EventApiData = branchApiData.events.first()
        val nextEvent: EventApiData = branchApiData.events.last()

        val currentEventDatePlaceText = "%s - %s • %s".format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place
        )

        currentEventDatePlace.text = currentEventDatePlaceText
        currentSpeakerName.text = currentEvent.speaker?.fullName
        currentSpeakerJob.text = currentEvent.speaker?.job
        currentEventTitle.text = currentEvent.title

        val nextEventDatePlaceText = "%s - %s • %s".format(
            currentEvent.startTime,
            currentEvent.endTime,
            currentEvent.place
        )

        nextEventDatePlace.text = nextEventDatePlaceText
        nextSpeakerName.text = nextEvent.speaker?.fullName
        nextSpeakerJob.text = nextEvent.speaker?.job
        nextEventTitle.text = nextEvent.title



        branchTitle.setOnClickListener {
            val intent: Intent = Intent(it.context, BranchAllEventsActivity::class.java)
            it.context.startActivity(intent)

        }
        currentBranchEvent.setOnClickListener {
            Toast.makeText(it.context, currentEventTitle.text, Toast.LENGTH_SHORT).show()
        }
        toFavouritesImageView.setOnClickListener {
            toFavouritesImageView.setImageResource(R.drawable.ic_favorite_fill)
        }

    }

}
