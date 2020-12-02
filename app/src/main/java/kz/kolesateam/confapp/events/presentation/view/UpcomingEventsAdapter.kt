package kz.kolesateam.confapp.events.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.models.HEADER_TYPE
import kz.kolesateam.confapp.events.data.models.UpcomingEventListItem

class UpcomingEventsAdapter : RecyclerView.Adapter<BaseViewHolder<UpcomingEventListItem>>() {

    private val branchDataList: MutableList<UpcomingEventListItem> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<UpcomingEventListItem> {
        return when (viewType) {
            HEADER_TYPE -> createHeaderViewHolder(parent)
            else -> createBranchViewHolder(parent)

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<UpcomingEventListItem>, position: Int) {
        holder.onBind(branchDataList[position])
    }

    override fun getItemCount(): Int = branchDataList.size

    override fun getItemViewType(position: Int): Int {
        return branchDataList[position].type
    }

    private fun createHeaderViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = HeaderViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.header_layout,
            parent,
            false
        )
    )
    private fun createBranchViewHolder(
        parent: ViewGroup
    ): BaseViewHolder<UpcomingEventListItem> = BranchViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.branch_list_item,
            parent,
            false
        )
    )

    fun setList(
        branchDataList: List<UpcomingEventListItem>
    ) {
        this.branchDataList.clear()
        this.branchDataList.addAll(branchDataList)
        notifyDataSetChanged()
    }
}