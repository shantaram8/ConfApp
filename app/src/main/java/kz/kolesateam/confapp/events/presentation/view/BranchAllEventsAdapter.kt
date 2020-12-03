package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.EventApiData

class BranchAllEventsAdapter(
    private val branchAllEventsClickListeners: UpcomingEventsClickListeners
) : RecyclerView.Adapter<BranchAllEventsViewHolder>() {

    private val branchAllEventsList: MutableList<EventApiData> = mutableListOf()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BranchAllEventsViewHolder = BranchAllEventsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_all_events_card_item,
            parent,
            false
        ),
        branchAllEventsClickListeners
    )

    override fun onBindViewHolder(holder: BranchAllEventsViewHolder, position: Int) {
        holder.onBind(branchAllEventsList[position])
    }

    override fun getItemCount(): Int = branchAllEventsList.size

    fun setList(
        branchEventsDataList: List<EventApiData>
    ) {
        branchAllEventsList.clear()
        branchAllEventsList.addAll(branchEventsDataList)
        notifyDataSetChanged()
    }
}
