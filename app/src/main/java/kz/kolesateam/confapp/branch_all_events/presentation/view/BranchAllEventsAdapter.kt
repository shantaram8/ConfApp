package kz.kolesateam.confapp.branch_all_events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.models.BranchAllEventsListItem
import kz.kolesateam.confapp.models.HEADER_TYPE
import kz.kolesateam.confapp.upcoming_events.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.upcoming_events.presentation.view.UpcomingEventsClickListeners

class BranchAllEventsAdapter(
    private val branchAllEventsClickListeners: UpcomingEventsClickListeners
) : RecyclerView.Adapter<BaseViewHolder<BranchAllEventsListItem>>() {

    private val branchAllEventsList: MutableList<BranchAllEventsListItem> = mutableListOf()
    override fun onBindViewHolder(holder: BaseViewHolder<BranchAllEventsListItem>, position: Int) {
        holder.onBind(branchAllEventsList[position])
    }

    override fun getItemCount(): Int = branchAllEventsList.size

    override fun getItemViewType(position: Int): Int {
        return branchAllEventsList[position].type
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BranchAllEventsListItem> {
        return when (viewType) {
            HEADER_TYPE -> createBranchTitleViewHolder(parent)
            else -> createBranchAllEventsViewHolder(parent)

        }
    }

    private fun createBranchTitleViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<BranchAllEventsListItem> = BranchTitleViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_title_layout,
            parent,
            false
        )
    )
    private fun createBranchAllEventsViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<BranchAllEventsListItem> = BranchAllEventsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_all_events_card_item,
            parent,
            false
        ),
        branchAllEventsClickListeners
    )

    fun setList(
        branchEventsDataList: List<BranchAllEventsListItem>
    ) {
        branchAllEventsList.clear()
        branchAllEventsList.addAll(branchEventsDataList)
        notifyDataSetChanged()
    }
}
